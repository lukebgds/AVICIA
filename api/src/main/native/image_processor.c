#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <CL/cl.h>

#define MAX_SOURCE_SIZE (0x100000)
#define STB_IMAGE_IMPLEMENTATION
#include "stb/stb_image.h"
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb/stb_image_write.h"

// Kernel OpenCL para processamento de imagens 
const char *kernelSource = 
"__kernel void processImage(\n"
"    __global const uchar4* input,\n"
"    __global uchar4* output,\n"
"    const int width,\n"
"    const int height,\n"
"    const float contrastFactor,\n"
"    const float brightnessFactor)\n"
"{\n"
"    int x = get_global_id(0);\n"
"    int y = get_global_id(1);\n"
"    \n"
"    if (x >= width || y >= height) return;\n"
"    \n"
"    int idx = y * width + x;\n"
"    uchar4 pixel = input[idx];\n"
"    \n"
"    // Ajuste de contraste e brilho\n"
"    float r = (float)pixel.x;\n"
"    float g = (float)pixel.y;\n"
"    float b = (float)pixel.z;\n"
"    \n"
"    r = ((r / 255.0f - 0.5f) * contrastFactor + 0.5f + brightnessFactor) * 255.0f;\n"
"    g = ((g / 255.0f - 0.5f) * contrastFactor + 0.5f + brightnessFactor) * 255.0f;\n"
"    b = ((b / 255.0f - 0.5f) * contrastFactor + 0.5f + brightnessFactor) * 255.0f;\n"
"    \n"
"    pixel.x = (uchar)clamp(r, 0.0f, 255.0f);\n"
"    pixel.y = (uchar)clamp(g, 0.0f, 255.0f);\n"
"    pixel.z = (uchar)clamp(b, 0.0f, 255.0f);\n"
"    \n"
"    output[idx] = pixel;\n"
"}\n"
"\n"
"__kernel void reduceNoise(\n"
"    __global const uchar4* input,\n"
"    __global uchar4* output,\n"
"    const int width,\n"
"    const int height,\n"
"    const int kernelSize)\n"
"{\n"
"    int x = get_global_id(0);\n"
"    int y = get_global_id(1);\n"
"    \n"
"    if (x >= width || y >= height) return;\n"
"    \n"
"    int halfKernel = kernelSize / 2;\n"
"    int4 sum = (int4)(0, 0, 0, 0);\n"
"    int count = 0;\n"
"    \n"
"    for (int dy = -halfKernel; dy <= halfKernel; dy++) {\n"
"        for (int dx = -halfKernel; dx <= halfKernel; dx++) {\n"
"            int nx = x + dx;\n"
"            int ny = y + dy;\n"
"            \n"
"            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {\n"
"                uchar4 pixel = input[ny * width + nx];\n"
"                sum.x += pixel.x;\n"
"                sum.y += pixel.y;\n"
"                sum.z += pixel.z;\n"
"                sum.w += pixel.w;\n"
"                count++;\n"
"            }\n"
"        }\n"
"    }\n"
"    \n"
"    uchar4 result;\n"
"    int c = (count > 0 ? count : 1;\n"
"    result.x = (uchar)(sum.x / c);\n"
"    result.y = (uchar)(sum.y / c);\n"
"    result.z = (uchar)(sum.z / c);\n"
"    result.w = (uchar)(sum.w / c);\n"
"    \n"
"    output[y * width + x] = result;\n"
"}\n"
"\n"
"__kernel void calculateStats(\n"
"    __global const float* data,\n"
"    __global float* results,\n"
"    const int n,\n"
"    __local float* localMem)\n"
"{\n"
"    int gid = get_global_id(0);\n"
"    int lid = get_local_id(0);\n"
"    int groupSize = get_local_size(0);\n"
"    float* localSum = localMem;\n"
"    float* localMax = localMem + groupSize;\n"
"    float* localMin = localMem + 2*groupSize;\n"
"\n"
"    float v = (gid < n) ? data[gid] : 0.0f;\n"
"\n"
"    localSum[lid] = (gid < n) ? v : 0.0f;\n"
"    localMax[lid] = (gid < n) ? v : -INFINITY;\n"
"    localMin[lid] = (gid < n) ? v : INFINITY;\n"
"\n"
"    barrier(CLK_LOCAL_MEM_FENCE);\n"
"\n"
"    for (int stride = groupSize / 2; stride > 0; stride /= 2) {\n"
"        if (lid < stride) {\n"
"            localSum[lid] += localSum[lid + stride];\n"
"            localMax[lid] = fmax(localMax[lid], localMax[lid + stride]);\n"
"            localMin[lid] = fmin(localMin[lid], localMin[lid + stride]);\n"
"        }\n"
"        barrier(CLK_LOCAL_MEM_FENCE);\n"
"    }\n"
"\n"
"    int float_to_int(float x) { return as_int(x); }\n"
"    float int_to_float(int x) { return as_float(x); }\n"
"\n"
"    if (lid == 0) {\n"
"        volatile __global int* sumPtr = (volatile __global int*)&results[0];\n"
"        int oldInt, newInt;\n"
"        float oldF, newF;\n" 
"        do {\n"
"            oldInt = *sumPtr;\n"
"            oldF = int_to_float(oldInt);\n            newF = oldF + localSum[0];\n            newInt = float_to_int(newF);\n" 
"        } while (atomic_cmpxchg(sumPtr, oldInt, newInt) != oldInt);\n"
"\n"
"        volatile __global int* maxPtr = (volatile __global int*)&results[1];\n"
"        int prevMaxInt;\n"
"        do {\n"
"            prevMaxInt = *maxPtr;\n" 
"            if (localMax[0] <= int_to_float(prevMaxInt)) break;\n" 
"        } while (atomic_cmpxchg(maxPtr, prevMaxInt, float_to_int(localMax[0])) != prevMaxInt);\n"
"\n"
"        volatile __global int* minPtr = (volatile __global int*)&results[2];\n"
"        int prevMinInt;\n"
"        do {\n"
"            prevMinInt = *minPtr;\n" 
"            if (localMin[0] >= int_to_float(prevMinInt)) break;\n"
"        } while (atomic_cmpxchg(minPtr, prevMinInt, float_to_int(localMin[0])) != prevMinInt);\n"
"    }\n"
"}\n";

// Estrutura para contexto OpenCL
typedef struct {
    cl_platform_id platform;
    cl_device_id device;
    cl_context context;
    cl_command_queue queue;
    cl_program program;
    int initialized;
} OpenCLContext;

// Estrutura para resultado de processamento
typedef struct {
    int success;
    char message[256];
    int width;
    int height;
    int channels;
} ProcessResult;

// Contexto global
static OpenCLContext g_context = {0};

// Função para inicializar OpenCL
int opencl_init() {
    if (g_context.initialized) {
        return 1;
    }

    cl_int ret;
    
    // 1. Obter plataforma
    ret = clGetPlatformIDs(1, &g_context.platform, NULL);
    if (ret != CL_SUCCESS) {
        fprintf(stderr, "Erro ao obter plataforma OpenCL: %d\n", ret);
        return 0;
    }

    // 2. Obter dispositivo GPU
    ret = clGetDeviceIDs(g_context.platform, CL_DEVICE_TYPE_GPU, 1, 
                         &g_context.device, NULL);
    if (ret != CL_SUCCESS) {
        // Tentar CPU como fallback
        ret = clGetDeviceIDs(g_context.platform, CL_DEVICE_TYPE_CPU, 1, 
                            &g_context.device, NULL);
        if (ret != CL_SUCCESS) {
            fprintf(stderr, "Erro ao obter dispositivo OpenCL: %d\n", ret);
            return 0;
        }
        printf("Usando CPU para OpenCL\n");
    } else {
        printf("Usando GPU para OpenCL\n");
    }

    // 3. Criar contexto
    g_context.context = clCreateContext(NULL, 1, &g_context.device, 
                                        NULL, NULL, &ret);
    if (ret != CL_SUCCESS) {
        fprintf(stderr, "Erro ao criar contexto: %d\n", ret);
        return 0;
    }

    // 4. Criar fila de comandos
    g_context.queue = clCreateCommandQueue(g_context.context, 
                                           g_context.device, 0, &ret);
    if (ret != CL_SUCCESS) {
        fprintf(stderr, "Erro ao criar fila de comandos: %d\n", ret);
        return 0;
    }

    // 5. Compilar programa
    size_t source_size = strlen(kernelSource);
    g_context.program = clCreateProgramWithSource(g_context.context, 1, 
                                                   &kernelSource, &source_size, &ret);
    if (ret != CL_SUCCESS) {
        fprintf(stderr, "Erro ao criar programa: %d\n", ret);
        return 0;
    }

    ret = clBuildProgram(g_context.program, 1, &g_context.device, 
                        NULL, NULL, NULL);
    if (ret != CL_SUCCESS) {
        // Obter log de compilação
        size_t log_size;
        clGetProgramBuildInfo(g_context.program, g_context.device, 
                             CL_PROGRAM_BUILD_LOG, 0, NULL, &log_size);
        char *log = (char*)malloc(log_size);
        clGetProgramBuildInfo(g_context.program, g_context.device, 
                             CL_PROGRAM_BUILD_LOG, log_size, log, NULL);
        fprintf(stderr, "Erro ao compilar programa:\n%s\n", log);
        free(log);
        return 0;
    }

    g_context.initialized = 1;
    printf("OpenCL inicializado com sucesso!\n");
    return 1;
}


// Função para processar imagem
int process_image(const char* input_path, const char* output_path,
                 float contrast, float brightness, ProcessResult* result) {
    
    if (!g_context.initialized) {
        if (!opencl_init()) {
            strcpy(result->message, "Erro ao inicializar OpenCL");
            result->success = 0;
            return 0;
        }
    }

    // Carregar imagem
    int width, height, channels;
    unsigned char *image = stbi_load(input_path, &width, &height, &channels, 4);
    if (!image) {
        sprintf(result->message, "Erro ao carregar imagem: %s", input_path);
        result->success = 0;
        return 0;
    }

    printf("Imagem carregada: %dx%d, %d canais\n", width, height, channels);

    size_t image_size = width * height * 4 * sizeof(unsigned char);
    unsigned char *output = (unsigned char*)malloc(image_size);

    cl_int ret;
    
    // Criar buffers OpenCL
    cl_mem input_buffer = clCreateBuffer(g_context.context, 
        CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
        image_size, image, &ret);
    
    cl_mem output_buffer = clCreateBuffer(g_context.context, 
        CL_MEM_WRITE_ONLY, image_size, NULL, &ret);

    // Criar e configurar kernel
    cl_kernel kernel = clCreateKernel(g_context.program, "processImage", &ret);
    if (ret != CL_SUCCESS) {
        sprintf(result->message, "Erro ao criar kernel: %d", ret);
        result->success = 0;
        goto cleanup;
    }

    clSetKernelArg(kernel, 0, sizeof(cl_mem), &input_buffer);
    clSetKernelArg(kernel, 1, sizeof(cl_mem), &output_buffer);
    clSetKernelArg(kernel, 2, sizeof(int), &width);
    clSetKernelArg(kernel, 3, sizeof(int), &height);
    clSetKernelArg(kernel, 4, sizeof(float), &contrast);
    clSetKernelArg(kernel, 5, sizeof(float), &brightness);

    // Executar kernel
    size_t global_work_size[2] = {(size_t)width, (size_t)height};
    ret = clEnqueueNDRangeKernel(g_context.queue, kernel, 2, NULL,
                                 global_work_size, NULL, 0, NULL, NULL);
    if (ret != CL_SUCCESS) {
        sprintf(result->message, "Erro ao executar kernel: %d", ret);
        result->success = 0;
        goto cleanup;
    }

    // Ler resultado
    ret = clEnqueueReadBuffer(g_context.queue, output_buffer, CL_TRUE, 0,
                             image_size, output, 0, NULL, NULL);
    if (ret != CL_SUCCESS) {
        sprintf(result->message, "Erro ao ler buffer: %d", ret);
        result->success = 0;
        goto cleanup;
    }

    // Salvar imagem
    if (!stbi_write_png(output_path, width, height, 4, output, width * 4)) {
        sprintf(result->message, "Erro ao salvar imagem: %s", output_path);
        result->success = 0;
        goto cleanup;
    }

    sprintf(result->message, "Imagem processada com sucesso");
    result->success = 1;
    result->width = width;
    result->height = height;
    result->channels = channels;

cleanup:
    clReleaseMemObject(input_buffer);
    clReleaseMemObject(output_buffer);
    clReleaseKernel(kernel);
    free(output);
    stbi_image_free(image);

    return result->success;
}

// Função para reduzir ruído
int reduce_noise(const char* input_path, const char* output_path,
                int kernel_size, ProcessResult* result) {
    
    if (!g_context.initialized) {
        if (!opencl_init()) {
            strcpy(result->message, "Erro ao inicializar OpenCL");
            result->success = 0;
            return 0;
        }
    }

    int width, height, channels;
    unsigned char *image = stbi_load(input_path, &width, &height, &channels, 4);
    if (!image) {
        sprintf(result->message, "Erro ao carregar imagem: %s", input_path);
        result->success = 0;
        return 0;
    }

    size_t image_size = width * height * 4 * sizeof(unsigned char);
    unsigned char *output = (unsigned char*)malloc(image_size);

    cl_int ret;
    cl_mem input_buffer = clCreateBuffer(g_context.context, 
        CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
        image_size, image, &ret);
    
    cl_mem output_buffer = clCreateBuffer(g_context.context, 
        CL_MEM_WRITE_ONLY, image_size, NULL, &ret);

    cl_kernel kernel = clCreateKernel(g_context.program, "reduceNoise", &ret);
    clSetKernelArg(kernel, 0, sizeof(cl_mem), &input_buffer);
    clSetKernelArg(kernel, 1, sizeof(cl_mem), &output_buffer);
    clSetKernelArg(kernel, 2, sizeof(int), &width);
    clSetKernelArg(kernel, 3, sizeof(int), &height);
    clSetKernelArg(kernel, 4, sizeof(int), &kernel_size);

    size_t global_work_size[2] = {(size_t)width, (size_t)height};
    clEnqueueNDRangeKernel(g_context.queue, kernel, 2, NULL,
                          global_work_size, NULL, 0, NULL, NULL);

    clEnqueueReadBuffer(g_context.queue, output_buffer, CL_TRUE, 0,
                       image_size, output, 0, NULL, NULL);

    stbi_write_png(output_path, width, height, 4, output, width * 4);

    sprintf(result->message, "Ruído reduzido com sucesso");
    result->success = 1;
    result->width = width;
    result->height = height;

    clReleaseMemObject(input_buffer);
    clReleaseMemObject(output_buffer);
    clReleaseKernel(kernel);
    free(output);
    stbi_image_free(image);

    return 1;
}

// Função para calcular estatísticas
int calculate_stats(float* data, int n, float* mean, float* max, float* min) {
    if (!g_context.initialized) {
        if (!opencl_init()) {
            return 0;
        }
    }

    float results[3] = {0.0f, -INFINITY, INFINITY};
    
    cl_int ret;
    cl_mem data_buffer = clCreateBuffer(g_context.context,
        CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR,
        n * sizeof(float), data, &ret);
    
    cl_mem results_buffer = clCreateBuffer(g_context.context,
        CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR,
        3 * sizeof(float), results, &ret);

    cl_kernel kernel = clCreateKernel(g_context.program, "calculateStats", &ret);
    clSetKernelArg(kernel, 0, sizeof(cl_mem), &data_buffer);
    clSetKernelArg(kernel, 1, sizeof(cl_mem), &results_buffer);
    clSetKernelArg(kernel, 2, sizeof(int), &n);

    size_t global_work_size = ((n + 255) / 256) * 256;
    size_t local_work_size = 256;
    clSetKernelArg(kernel, 3, local_work_size * 3 * sizeof(float), NULL);
    clEnqueueNDRangeKernel(g_context.queue, kernel, 1, NULL,
                          &global_work_size, &local_work_size, 0, NULL, NULL);

    clEnqueueReadBuffer(g_context.queue, results_buffer, CL_TRUE, 0,
                       3 * sizeof(float), results, 0, NULL, NULL);

    *mean = (n>0) ? results[0] / n : 0.0f;
    *max = results[1];
    *min = results[2];

    clReleaseMemObject(data_buffer);
    clReleaseMemObject(results_buffer);
    clReleaseKernel(kernel);

    return 1;
}

// Função para limpeza
void opencl_cleanup() {
    if (g_context.initialized) {
        clReleaseProgram(g_context.program);
        clReleaseCommandQueue(g_context.queue);
        clReleaseContext(g_context.context);
        g_context.initialized = 0;
        printf("OpenCL finalizado\n");
    }
}

// Função main para testes standalone
int main(int argc, char** argv) {
    if (argc < 4) {
        printf("Uso: %s <input> <output> <contrast> [brightness]\n", argv[0]);
        return 1;
    }

    const char* input = argv[1];
    const char* output = argv[2];
    float contrast = atof(argv[3]);
    float brightness = (argc > 4) ? atof(argv[4]) : 0.0f;

    ProcessResult result;
    
    if (process_image(input, output, contrast, brightness, &result)) {
        printf("Sucesso: %s\n", result.message);
        printf("Dimensões: %dx%d\n", result.width, result.height);
    } else {
        fprintf(stderr, "Erro: %s\n", result.message);
        return 1;
    }

    opencl_cleanup();
    return 0;
}