#ifndef CL_TARGET_OPENCL_VERSION
#define CL_TARGET_OPENCL_VERSION 300
#endif

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>
#include <CL/cl.h>

#define MAX_SOURCE_SIZE (0x100000)

#define STB_IMAGE_IMPLEMENTATION
#include "stb/stb_image.h"

#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb/stb_image_write.h"

// Kernel OpenCL para processamento de imagens 
const char *kernelSource =
"// Helpers (opencl C)\n"
"inline int float_to_int(float x) { return as_int(x); }\n"
"inline float int_to_float(int x) { return as_float(x); }\n"
"\n"
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
"    if (x >= width || y >= height) return;\n"
"    int idx = y * width + x;\n"
"    uchar4 pixel = input[idx];\n"
"    float r = (float)pixel.x;\n"
"    float g = (float)pixel.y;\n"
"    float b = (float)pixel.z;\n"
"    r = ((r / 255.0f - 0.5f) * contrastFactor + 0.5f + brightnessFactor) * 255.0f;\n"
"    g = ((g / 255.0f - 0.5f) * contrastFactor + 0.5f + brightnessFactor) * 255.0f;\n"
"    b = ((b / 255.0f - 0.5f) * contrastFactor + 0.5f + brightnessFactor) * 255.0f;\n"
"    pixel.x = (uchar)clamp(r, 0.0f, 255.0f);\n"
"    pixel.y = (uchar)clamp(g, 0.0f, 255.0f);\n"
"    pixel.z = (uchar)clamp(b, 0.0f, 255.0f);\n"
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
"    if (x >= width || y >= height) return;\n"
"    int halfKernel = kernelSize / 2;\n"
"    int sumR = 0, sumG = 0, sumB = 0, sumA = 0;\n"
"    int count = 0;\n"
"    for (int dy = -halfKernel; dy <= halfKernel; dy++) {\n"
"        for (int dx = -halfKernel; dx <= halfKernel; dx++) {\n"
"            int nx = x + dx;\n"
"            int ny = y + dy;\n"
"            if (nx >= 0 && nx < width && ny >= 0 && ny < height) {\n"
"                uchar4 p = input[ny * width + nx];\n"
"                sumR += p.x; sumG += p.y; sumB += p.z; sumA += p.w;\n"
"                count++;\n"
"            }\n"
"        }\n"
"    }\n"
"    int c = (count > 0) ? count : 1;\n"
"    uchar4 result;\n"
"    result.x = (uchar)(sumR / c);\n"
"    result.y = (uchar)(sumG / c);\n"
"    result.z = (uchar)(sumB / c);\n"
"    result.w = (uchar)(sumA / c);\n"
"    output[y * width + x] = result;\n"
"}\n"
"\n"
"// calculateStats: cada grupo (work-group) produz 3 floats: sum, max, min\n"
"__kernel void calculateStats(\n"
"    __global const float* data,\n"
"    __global float* groupResults, // size: num_groups * 3\n"
"    const int n,\n"
"    __local float* localMem)\n"
"{\n"
"    int gid = get_global_id(0);\n"
"    int lid = get_local_id(0);\n"
"    int groupSize = get_local_size(0);\n"
"    int groupId = get_group_id(0);\n"
"\n"
"    float v = (gid < n) ? data[gid] : 0.0f;\n"
"    float* localSum = localMem; // groupSize floats\n"
"    float* localMax = localMem + groupSize; // groupSize floats\n"
"    float* localMin = localMem + 2 * groupSize; // groupSize floats\n"
"\n"
"    localSum[lid] = (gid < n) ? v : 0.0f;\n"
"    localMax[lid] = (gid < n) ? v : -INFINITY;\n"
"    localMin[lid] = (gid < n) ? v : INFINITY;\n"
"\n"
"    barrier(CLK_LOCAL_MEM_FENCE);\n"
"    for (int stride = groupSize / 2; stride > 0; stride /= 2) {\n"
"        if (lid < stride) {\n"
"            localSum[lid] += localSum[lid + stride];\n"
"            localMax[lid] = fmax(localMax[lid], localMax[lid + stride]);\n"
"            localMin[lid] = fmin(localMin[lid], localMin[lid + stride]);\n"
"        }\n"
"        barrier(CLK_LOCAL_MEM_FENCE);\n"
"    }\n"
"\n"
"    if (lid == 0) {\n"
"        // escreve sum, max, min para este grupo\n"
"        int outIdx = groupId * 3;\n" 
"        groupResults[outIdx + 0] = localSum[0];\n"
"        groupResults[outIdx + 1] = localMax[0];\n" 
"        groupResults[outIdx + 2] = localMin[0];\n"
"    }\n"
"}\n"
"\n";

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

// Exibir Erro
static void print_cl_error(const char* prefix, cl_int err) {
    fprintf(stderr, "%s (code %d)\n", prefix, err);
}

// Inicializa OpenCL
int opencl_init() {
    if (g_context.initialized) {
        return 1;
    }

    cl_int ret;

    // Plataforma
    ret = clGetPlatformIDs(1, &g_context.platform, NULL);
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro ao obter plataforma OpenCL", ret);
        return 0;
    }

    // Dispositivo (GPU preferido, senão CPU)
    ret = clGetDeviceIDs(g_context.platform, CL_DEVICE_TYPE_GPU, 1, &g_context.device, NULL);
    if (ret != CL_SUCCESS) {
        ret = clGetDeviceIDs(g_context.platform, CL_DEVICE_TYPE_CPU, 1, &g_context.device, NULL);
        if (ret != CL_SUCCESS) {
            print_cl_error("Erro ao obter dispositivo OpenCL", ret);
            return 0;
        }
        printf("Usando CPU para OpenCL\n");
    } else {
        printf("Usando GPU para OpenCL\n");
    }

    // Contexto
    g_context.context = clCreateContext(NULL, 1, &g_context.device, NULL, NULL, &ret);
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro ao criar contexto", ret);
        return 0;
    }

    // Fila de comandos: usar clCreateCommandQueueWithProperties quando disponível
#if defined(CL_VERSION_2_0)
    g_context.queue = clCreateCommandQueueWithProperties(g_context.context, g_context.device, NULL, &ret);
#else
    g_context.queue = clCreateCommandQueue(g_context.context, g_context.device, 0, &ret);
#endif
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro ao criar fila de comandos", ret);
        clReleaseContext(g_context.context);
        return 0;
    }

    // Programa
    size_t source_size = strlen(kernelSource);
    g_context.program = clCreateProgramWithSource(g_context.context, 1, &kernelSource, &source_size, &ret);
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro ao criar programa", ret);
        clReleaseCommandQueue(g_context.queue);
        clReleaseContext(g_context.context);
        return 0;
    }

    ret = clBuildProgram(g_context.program, 1, &g_context.device, NULL, NULL, NULL);
    if (ret != CL_SUCCESS) {
        // obter log
        size_t log_size = 0;
        clGetProgramBuildInfo(g_context.program, g_context.device, CL_PROGRAM_BUILD_LOG, 0, NULL, &log_size);
        char *log = (char*)malloc(log_size + 1);
        if (log) {
            clGetProgramBuildInfo(g_context.program, g_context.device, CL_PROGRAM_BUILD_LOG, log_size, log, NULL);
            log[log_size] = '\0';
            fprintf(stderr, "Erro ao compilar programa:\n%s\n", log);
            free(log);
        } else {
            fprintf(stderr, "Erro ao compilar programa. (log unavailable)\n");
        }
        clReleaseProgram(g_context.program);
        clReleaseCommandQueue(g_context.queue);
        clReleaseContext(g_context.context);
        return 0;
    }

    g_context.initialized = 1;
    printf("OpenCL inicializado com sucesso!\n");
    return 1;
}

// Processar imagem (processImage kernel)
int process_image(const char* input_path, const char* output_path,
                 float contrast, float brightness, ProcessResult* result) {

    if (!g_context.initialized) {
        if (!opencl_init()) {
            strcpy(result->message, "Erro ao inicializar OpenCL");
            result->success = 0;
            return 0;
        }
    }

    // Teste de depuração
    FILE *f = fopen(input_path, "rb");
    if (!f) {
        printf("DEBUG: fopen falhou! Caminho: %s\n", input_path);
    } else {
        printf("DEBUG: fopen funcionou!\n");
        fclose(f);
    }

    int width, height, channels;
    unsigned char *image = stbi_load(input_path, &width, &height, &channels, 4);
    if (!image) {
        sprintf(result->message, "Erro ao carregar imagem: %s", input_path);
        result->success = 0;
        return 0;
    }

    printf("Imagem carregada: %dx%d, %d canais (forçado 4)\n", width, height, channels);
    size_t image_size = (size_t)width * (size_t)height * 4 * sizeof(unsigned char);
    unsigned char *output = (unsigned char*)malloc(image_size);
    if (!output) {
        stbi_image_free(image);
        strcpy(result->message, "Memória insuficiente para saída");
        result->success = 0;
        return 0;
    }

    cl_int ret;
    cl_mem input_buffer = clCreateBuffer(g_context.context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR, image_size, image, &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clCreateBuffer input", ret); goto cleanup; }
    cl_mem output_buffer = clCreateBuffer(g_context.context, CL_MEM_WRITE_ONLY, image_size, NULL, &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clCreateBuffer output", ret); clReleaseMemObject(input_buffer); goto cleanup; }

    cl_kernel kernel = clCreateKernel(g_context.program, "processImage", &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro ao criar kernel processImage", ret); clReleaseMemObject(input_buffer); clReleaseMemObject(output_buffer); goto cleanup; }

    ret  = clSetKernelArg(kernel, 0, sizeof(cl_mem), &input_buffer);
    ret |= clSetKernelArg(kernel, 1, sizeof(cl_mem), &output_buffer);
    ret |= clSetKernelArg(kernel, 2, sizeof(int), &width);
    ret |= clSetKernelArg(kernel, 3, sizeof(int), &height);
    ret |= clSetKernelArg(kernel, 4, sizeof(float), &contrast);
    ret |= clSetKernelArg(kernel, 5, sizeof(float), &brightness);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clSetKernelArg processImage", ret); clReleaseKernel(kernel); clReleaseMemObject(input_buffer); clReleaseMemObject(output_buffer); goto cleanup; }

    size_t global_work_size[2] = {(size_t)width, (size_t)height};
    ret = clEnqueueNDRangeKernel(g_context.queue, kernel, 2, NULL, global_work_size, NULL, 0, NULL, NULL);
    if (ret != CL_SUCCESS) { print_cl_error("Erro ao enfileirar kernel processImage", ret); clReleaseKernel(kernel); clReleaseMemObject(input_buffer); clReleaseMemObject(output_buffer); goto cleanup; }

    clFinish(g_context.queue); // garantir conclusão

    ret = clEnqueueReadBuffer(g_context.queue, output_buffer, CL_TRUE, 0, image_size, output, 0, NULL, NULL);
    if (ret != CL_SUCCESS) { print_cl_error("Erro ao ler buffer output", ret); clReleaseKernel(kernel); clReleaseMemObject(input_buffer); clReleaseMemObject(output_buffer); goto cleanup; }

    if (!stbi_write_png(output_path, width, height, 4, output, width * 4)) {
        sprintf(result->message, "Erro ao salvar imagem: %s", output_path);
        result->success = 0;
        clReleaseKernel(kernel);
        clReleaseMemObject(input_buffer);
        clReleaseMemObject(output_buffer);
        goto cleanup;
    }

    sprintf(result->message, "Imagem processada com sucesso");
    result->success = 1;
    result->width = width;
    result->height = height;
    result->channels = 4;

    clReleaseKernel(kernel);
    clReleaseMemObject(input_buffer);
    clReleaseMemObject(output_buffer);

cleanup:
    free(output);
    stbi_image_free(image);
    return result->success;
}

// Reduz ruído (reduceNoise kernel)
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

    size_t image_size = (size_t)width * (size_t)height * 4 * sizeof(unsigned char);
    unsigned char *output = (unsigned char*)malloc(image_size);
    if (!output) { stbi_image_free(image); strcpy(result->message, "Memória insuficiente"); result->success = 0; return 0; }

    cl_int ret;
    cl_mem input_buffer = clCreateBuffer(g_context.context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR, image_size, image, &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clCreateBuffer input", ret); goto cleanup_noise; }
    cl_mem output_buffer = clCreateBuffer(g_context.context, CL_MEM_WRITE_ONLY, image_size, NULL, &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clCreateBuffer output", ret); clReleaseMemObject(input_buffer); goto cleanup_noise; }

    cl_kernel kernel = clCreateKernel(g_context.program, "reduceNoise", &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro criar kernel reduceNoise", ret); clReleaseMemObject(input_buffer); clReleaseMemObject(output_buffer); goto cleanup_noise; }

    ret  = clSetKernelArg(kernel, 0, sizeof(cl_mem), &input_buffer);
    ret |= clSetKernelArg(kernel, 1, sizeof(cl_mem), &output_buffer);
    ret |= clSetKernelArg(kernel, 2, sizeof(int), &width);
    ret |= clSetKernelArg(kernel, 3, sizeof(int), &height);
    ret |= clSetKernelArg(kernel, 4, sizeof(int), &kernel_size);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clSetKernelArg reduceNoise", ret); clReleaseKernel(kernel); clReleaseMemObject(input_buffer); clReleaseMemObject(output_buffer); goto cleanup_noise; }

    size_t global_work_size[2] = {(size_t)width, (size_t)height};
    ret = clEnqueueNDRangeKernel(g_context.queue, kernel, 2, NULL, global_work_size, NULL, 0, NULL, NULL);
    if (ret != CL_SUCCESS) { print_cl_error("Erro enfileirar reduceNoise", ret); clReleaseKernel(kernel); clReleaseMemObject(input_buffer); clReleaseMemObject(output_buffer); goto cleanup_noise; }

    clFinish(g_context.queue);

    ret = clEnqueueReadBuffer(g_context.queue, output_buffer, CL_TRUE, 0, image_size, output, 0, NULL, NULL);
    if (ret != CL_SUCCESS) { print_cl_error("Erro ler buffer reduceNoise", ret); clReleaseKernel(kernel); clReleaseMemObject(input_buffer); clReleaseMemObject(output_buffer); goto cleanup_noise; }

    if (!stbi_write_png(output_path, width, height, 4, output, width * 4)) {
        sprintf(result->message, "Erro ao salvar imagem: %s", output_path);
        result->success = 0;
        clReleaseKernel(kernel);
        clReleaseMemObject(input_buffer);
        clReleaseMemObject(output_buffer);
        goto cleanup_noise;
    }

    sprintf(result->message, "Ruído reduzido com sucesso");
    result->success = 1;
    result->width = width;
    result->height = height;

    clReleaseKernel(kernel);
    clReleaseMemObject(input_buffer);
    clReleaseMemObject(output_buffer);

cleanup_noise:
    free(output);
    stbi_image_free(image);
    return result->success;
}

// calcular estatísticas: host faz redução final após kernel por grupo
int calculate_stats(float* data, int n, float* mean, float* max_val, float* min_val) {
    if (!g_context.initialized) {
        if (!opencl_init()) {
            return 0;
        }
    }

    if (n <= 0) {
        *mean = 0.0f; *max_val = -INFINITY; *min_val = INFINITY;
        return 1;
    }

    cl_int ret;
    cl_mem data_buffer = clCreateBuffer(g_context.context, CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR, (size_t)n * sizeof(float), data, &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clCreateBuffer data", ret); return 0; }

    // dimensões de trabalho
    size_t local_work_size = 256;
    size_t global_work_size = ((size_t)n + local_work_size - 1) / local_work_size * local_work_size;
    size_t num_groups = global_work_size / local_work_size;

    // groupResults: cada grupo escreve 3 floats (sum,max,min)
    size_t group_results_size = num_groups * 3 * sizeof(float);
    float* host_group_results = (float*)malloc(group_results_size);
    if (!host_group_results) { clReleaseMemObject(data_buffer); return 0; }
    // inicializar com 0, -inf, +inf
    for (size_t gi = 0; gi < (size_t)num_groups; ++gi) {
        host_group_results[gi*3 + 0] = 0.0f;
        host_group_results[gi*3 + 1] = -INFINITY;
        host_group_results[gi*3 + 2] = INFINITY;
    }

    cl_mem results_buffer = clCreateBuffer(g_context.context, CL_MEM_READ_WRITE | CL_MEM_COPY_HOST_PTR, group_results_size, host_group_results, &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clCreateBuffer results", ret); free(host_group_results); clReleaseMemObject(data_buffer); return 0; }

    cl_kernel kernel = clCreateKernel(g_context.program, "calculateStats", &ret);
    if (ret != CL_SUCCESS) { print_cl_error("Erro criar kernel calculateStats", ret); clReleaseMemObject(data_buffer); clReleaseMemObject(results_buffer); free(host_group_results); return 0; }

    ret  = clSetKernelArg(kernel, 0, sizeof(cl_mem), &data_buffer);
    ret |= clSetKernelArg(kernel, 1, sizeof(cl_mem), &results_buffer);
    ret |= clSetKernelArg(kernel, 2, sizeof(int), &n);
    // local mem: local_work_size * 3 floats
    ret |= clSetKernelArg(kernel, 3, local_work_size * 3 * sizeof(float), NULL);
    if (ret != CL_SUCCESS) { print_cl_error("Erro clSetKernelArg calculateStats", ret); clReleaseKernel(kernel); clReleaseMemObject(data_buffer); clReleaseMemObject(results_buffer); free(host_group_results); return 0; }

    ret = clEnqueueNDRangeKernel(g_context.queue, kernel, 1, NULL, &global_work_size, &local_work_size, 0, NULL, NULL);
    if (ret != CL_SUCCESS) { print_cl_error("Erro enfileirar calculateStats", ret); clReleaseKernel(kernel); clReleaseMemObject(data_buffer); clReleaseMemObject(results_buffer); free(host_group_results); return 0; }

    clFinish(g_context.queue);

    ret = clEnqueueReadBuffer(g_context.queue, results_buffer, CL_TRUE, 0, group_results_size, host_group_results, 0, NULL, NULL);
    if (ret != CL_SUCCESS) { print_cl_error("Erro ler results_buffer", ret); clReleaseKernel(kernel); clReleaseMemObject(data_buffer); clReleaseMemObject(results_buffer); free(host_group_results); return 0; }

    // redução final no host
    double total_sum = 0.0;
    float overall_max = -INFINITY;
    float overall_min = INFINITY;
    for (size_t gi = 0; gi < num_groups; ++gi) {
        float gsum = host_group_results[gi*3 + 0];
        float gmax = host_group_results[gi*3 + 1];
        float gmin = host_group_results[gi*3 + 2];
        total_sum += (double)gsum;
        if (gmax > overall_max) overall_max = gmax;
        if (gmin < overall_min) overall_min = gmin;
    }

    *mean = (float)(total_sum / (double)n);
    *max_val = overall_max;
    *min_val = overall_min;

    clReleaseKernel(kernel);
    clReleaseMemObject(data_buffer);
    clReleaseMemObject(results_buffer);
    free(host_group_results);
    return 1;
}

// limpeza
void opencl_cleanup() {
    if (g_context.initialized) {
        clReleaseProgram(g_context.program);
#if defined(CL_VERSION_2_0)
        clReleaseCommandQueue(g_context.queue);
#else
        clReleaseCommandQueue(g_context.queue);
#endif
        clReleaseContext(g_context.context);
        g_context.initialized = 0;
        printf("OpenCL finalizado\n");
    }
}

// main de testes
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