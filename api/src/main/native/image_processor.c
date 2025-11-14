#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <CL/cl.h>

#define MAX_SOURCE_SIZE (0x100000)
#define STB_IMAGE_IMPLEMENTATION
#include "stb_image.h"
#define STB_IMAGE_WRITE_IMPLEMENTATION
#include "stb_image_write.h"

// Kernel OpenCL
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
"    result.x = (uchar)(sum.x / count);\n"
"    result.y = (uchar)(sum.y / count);\n"
"    result.z = (uchar)(sum.z / count);\n"
"    result.w = (uchar)(sum.w / count);\n"
"    \n"
"    output[y * width + x] = result;\n"
"}\n"
"\n"
"__kernel void calculateStats(\n"
"    __global const float* data,\n"
"    __global float* results,\n"
"    const int n)\n"
"{\n"
"    int gid = get_global_id(0);\n"
"    int lid = get_local_id(0);\n"
"    int groupSize = get_local_size(0);\n"
"    \n"
"    __local float localSum[256];\n"
"    __local float localMax[256];\n"
"    __local float localMin[256];\n"
"    \n"
"    if (gid < n) {\n"
"        localSum[lid] = data[gid];\n"
"        localMax[lid] = data[gid];\n"
"        localMin[lid] = data[gid];\n"
"    } else {\n"
"        localSum[lid] = 0.0f;\n"
"        localMax[lid] = -INFINITY;\n"
"        localMin[lid] = INFINITY;\n"
"    }\n"
"    \n"
"    barrier(CLK_LOCAL_MEM_FENCE);\n"
"    \n"
"    // Redução paralela\n"
"    for (int stride = groupSize / 2; stride > 0; stride /= 2) {\n"
"        if (lid < stride) {\n"
"            localSum[lid] += localSum[lid + stride];\n"
"            localMax[lid] = max(localMax[lid], localMax[lid + stride]);\n"
"            localMin[lid] = min(localMin[lid], localMin[lid + stride]);\n"
"        }\n"
"        barrier(CLK_LOCAL_MEM_FENCE);\n"
"    }\n"
"    \n"
"    if (lid == 0) {\n"
"        atomic_add_global(&results[0], localSum[0]);\n"
"        // Para max e min, usamos compare-and-swap\n"
"        float oldMax = results[1];\n"
"        while (localMax[0] > oldMax) {\n"
"            float expected = oldMax;\n"
"            oldMax = atom_cmpxchg(&results[1], as_int(expected), as_int(localMax[0]));\n"
"        }\n"
"        float oldMin = results[2];\n"
"        while (localMin[0] < oldMin) {\n"
"            float expected = oldMin;\n"
"            oldMin = atom_cmpxchg(&results[2], as_int(expected), as_int(localMin[0]));\n"
"        }\n"
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