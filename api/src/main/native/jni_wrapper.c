// Wrapper para fazer integração com o JAVA

#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Declarações das funções do image_processor.c
extern int opencl_init();
extern int process_image(const char* input_path, const char* output_path,
                        float contrast, float brightness, void* result);
extern int reduce_noise(const char* input_path, const char* output_path,
                       int kernel_size, void* result);
extern int calculate_stats(float* data, int n, float* mean, float* max, float* min);
extern void opencl_cleanup();

// Estrutura de resultado
typedef struct {
    int success;
    char message[256];
    int width;
    int height;
    int channels;
} ProcessResult;

/*
 * Class:     com_avicia_native_ImageProcessorNative
 * Method:    initialize
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_avicia_native_ImageProcessorNative_initialize
  (JNIEnv *env, jobject obj) {
    return (jboolean)opencl_init();
}