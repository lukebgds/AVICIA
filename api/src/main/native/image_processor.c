
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

#include "kernels.h"


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

    // Carregar Kernel
    printf("Carregando kernels...\n");
    char* kernel_source = get_all_kernels();

    if (!kernel_source) {
        fprintf(stderr, "Erro ao carregar kernels\n");
        clReleaseCommandQueue(g_context.queue);
        clReleaseContext(g_context.context);
        return 0;
    }

    // Criar programa
    size_t source_size = strlen(kernel_source);
    g_context.program = clCreateProgramWithSource(g_context.context, 1, (const char**)&kernel_source, &source_size, &ret);

    if (ret != CL_SUCCESS) {
        print_cl_error("Erro ao criar programa", ret);
        clReleaseCommandQueue(g_context.queue);
        clReleaseContext(g_context.context);
        return 0;
    }

    // Compilar programa
    ret = clBuildProgram(g_context.program, 1, &g_context.device, NULL, NULL, NULL);

    if (ret != CL_SUCCESS) {
        
        // obter log de compilação
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

// Processar imagem
int process_image(const char* input_path, const char* output_path,
                 float contrast, float brightness, float saturation,
                 ProcessResult* result) {
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
    
    printf("Processando: %dx%d | C:%.2f B:%.2f S:%.2f\n", 
           width, height, contrast, brightness, saturation);

    size_t image_size = (size_t)width * (size_t)height * 4 * sizeof(unsigned char);
    unsigned char *output = (unsigned char*)malloc(image_size);
    if (!output) {
        stbi_image_free(image);
        strcpy(result->message, "Memória insuficiente");
        result->success = 0;
        return 0;
    }

    cl_int ret;
    cl_mem input_buffer = clCreateBuffer(g_context.context, 
        CL_MEM_READ_ONLY | CL_MEM_COPY_HOST_PTR, 
        image_size, image, &ret);
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro criar buffer input", ret);
        goto cleanup;
    }

    cl_mem output_buffer = clCreateBuffer(g_context.context, 
        CL_MEM_WRITE_ONLY, image_size, NULL, &ret);
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro criar buffer output", ret);
        clReleaseMemObject(input_buffer);
        goto cleanup;
    }

    cl_kernel kernel = clCreateKernel(g_context.program, "processImage", &ret);
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro criar kernel processImage", ret);
        clReleaseMemObject(input_buffer);
        clReleaseMemObject(output_buffer);
        goto cleanup;
    }

    ret  = clSetKernelArg(kernel, 0, sizeof(cl_mem), &input_buffer);
    ret |= clSetKernelArg(kernel, 1, sizeof(cl_mem), &output_buffer);
    ret |= clSetKernelArg(kernel, 2, sizeof(int), &width);
    ret |= clSetKernelArg(kernel, 3, sizeof(int), &height);
    ret |= clSetKernelArg(kernel, 4, sizeof(float), &contrast);
    ret |= clSetKernelArg(kernel, 5, sizeof(float), &brightness);
    ret |= clSetKernelArg(kernel, 6, sizeof(float), &saturation);
    
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro configurar argumentos kernel", ret);
        clReleaseKernel(kernel);
        clReleaseMemObject(input_buffer);
        clReleaseMemObject(output_buffer);
        goto cleanup;
    }

    size_t global_work_size[2] = {(size_t)width, (size_t)height};
    ret = clEnqueueNDRangeKernel(g_context.queue, kernel, 2, NULL, 
                                 global_work_size, NULL, 0, NULL, NULL);
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro executar kernel", ret);
        clReleaseKernel(kernel);
        clReleaseMemObject(input_buffer);
        clReleaseMemObject(output_buffer);
        goto cleanup;
    }

    clFinish(g_context.queue);

    ret = clEnqueueReadBuffer(g_context.queue, output_buffer, CL_TRUE, 0, 
                              image_size, output, 0, NULL, NULL);
    if (ret != CL_SUCCESS) {
        print_cl_error("Erro ler buffer output", ret);
        clReleaseKernel(kernel);
        clReleaseMemObject(input_buffer);
        clReleaseMemObject(output_buffer);
        goto cleanup;
    }

    if (!stbi_write_png(output_path, width, height, 4, output, width * 4)) {
        sprintf(result->message, "Erro ao salvar: %s", output_path);
        result->success = 0;
        clReleaseKernel(kernel);
        clReleaseMemObject(input_buffer);
        clReleaseMemObject(output_buffer);
        goto cleanup;
    }

    sprintf(result->message, "Sucesso!");
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

void opencl_cleanup() {
    if (g_context.initialized) {
        clReleaseProgram(g_context.program);
        clReleaseCommandQueue(g_context.queue);
        clReleaseContext(g_context.context);
        g_context.initialized = 0;
        printf("OpenCL finalizado\n");
    }
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

// main de testes 
int main(int argc, char** argv) {
    if (argc < 5) {
        printf("=== Processador de Imagens OpenCL ===\n\n");
        printf("Uso: %s <input> <output> <contrast> <brightness> <saturation>\n\n", argv[0]);
        printf("Parametros:\n");
        printf("  contrast    - Contraste (1.0 = normal)\n");
        printf("  brightness  - Brilho (0.0 = normal, -0.5 a +0.5)\n");
        printf("  saturation  - Saturacao (1.0 = normal, 0.0 = P&B)\n\n");
        printf("Exemplos:\n");
        printf("  Normal:         %s in.jpg out.jpg 1.0 0.0 1.0\n", argv[0]);
        printf("  Mais vibrante:  %s in.jpg out.jpg 1.2 0.1 1.5\n", argv[0]);
        printf("  Preto e branco: %s in.jpg out.jpg 1.0 0.0 0.0\n", argv[0]);
        printf("  Escurecido:     %s in.jpg out.jpg 1.0 -0.2 0.8\n\n", argv[0]);
        return 1;
    }

    const char* input = argv[1];
    const char* output = argv[2];
    float contrast = atof(argv[3]);
    float brightness = atof(argv[4]);
    float saturation = atof(argv[5]);

    printf("\n=== Processamento OpenCL ===\n");
    printf("Input:      %s\n", input);
    printf("Output:     %s\n", output);
    printf("Contraste:  %.2f\n", contrast);
    printf("Brilho:     %.2f\n", brightness);
    printf("Saturacao:  %.2f\n", saturation);
    printf("============================\n\n");

    ProcessResult result;
    if (process_image(input, output, contrast, brightness, saturation, &result)) {
        printf("\n✓ %s\n", result.message);
        printf("  Dimensoes: %dx%d\n", result.width, result.height);
        printf("  Salvo em: %s\n\n", output);
    } else {
        fprintf(stderr, "\n✗ Erro: %s\n\n", result.message);
        opencl_cleanup();
        return 1;
    }

    opencl_cleanup();
    return 0;
}