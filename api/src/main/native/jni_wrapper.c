#include <jni.h>
#include <stdio.h>
#include <string.h>

#include "com_avicia_api_util_jni_image_ImageProcessorNative.h"

// Importar funções C reais
extern int opencl_init();

extern int opencl_cleanup();

extern int process_image(const char* input_path, const char* output_path,
                        float contrast, float brightness, void* result);

extern int reduce_noise(const char* input_path, const char* output_path,
                       int kernel_size, void* result);

// Struct correspondente ao image_processor.c
typedef struct {
    int success;
    char message[256];
    int width;
    int height;
    int channels;
} ProcessResult;


// ------------------------------------------------------------------------------------
// Função auxiliar: preencher objeto Java ProcessResult
// ------------------------------------------------------------------------------------
void fillJavaResult(JNIEnv* env, jobject jResult, ProcessResult* res) {
    jclass cls = (*env)->GetObjectClass(env, jResult);

    jfieldID fidSuccess  = (*env)->GetFieldID(env, cls, "success", "Z");
    jfieldID fidMessage  = (*env)->GetFieldID(env, cls, "message", "Ljava/lang/String;");
    jfieldID fidWidth    = (*env)->GetFieldID(env, cls, "width", "I");
    jfieldID fidHeight   = (*env)->GetFieldID(env, cls, "height", "I");
    jfieldID fidChannels = (*env)->GetFieldID(env, cls, "channels", "I");

    (*env)->SetBooleanField(env, jResult, fidSuccess, (jboolean)res->success);

    jstring jmsg = (*env)->NewStringUTF(env, res->message);
    (*env)->SetObjectField(env, jResult, fidMessage, jmsg);

    (*env)->SetIntField(env, jResult, fidWidth, res->width);
    (*env)->SetIntField(env, jResult, fidHeight, res->height);
    (*env)->SetIntField(env, jResult, fidChannels, res->channels);
}


// ------------------------------------------------------------------------------------
// JNI: opencl_init
// ------------------------------------------------------------------------------------
JNIEXPORT jboolean JNICALL Java_com_avicia_api_util_jni_image_ImageProcessorNative_openclInit
  (JNIEnv* env, jclass clazz) {

    int ok = opencl_init();
    return ok ? JNI_TRUE : JNI_FALSE;
}



// ------------------------------------------------------------------------------------
// JNI: processImage
// ------------------------------------------------------------------------------------
JNIEXPORT jboolean JNICALL Java_com_avicia_api_util_jni_image_ImageProcessorNative_processImage
  (JNIEnv* env, jclass clazz,
   jstring jInput, jstring jOutput,
   jfloat contrast, jfloat brightness,
   jobject jResult)
{
    const char* input  = (*env)->GetStringUTFChars(env, jInput, NULL);
    const char* output = (*env)->GetStringUTFChars(env, jOutput, NULL);

    ProcessResult res = {0};

    int ok = process_image(input, output, contrast, brightness, &res);

    fillJavaResult(env, jResult, &res);

    (*env)->ReleaseStringUTFChars(env, jInput, input);
    (*env)->ReleaseStringUTFChars(env, jOutput, output);

    return ok ? JNI_TRUE : JNI_FALSE;
}



// ------------------------------------------------------------------------------------
// JNI: reduceNoise
// ------------------------------------------------------------------------------------
JNIEXPORT jboolean JNICALL Java_com_avicia_api_util_jni_image_ImageProcessorNative_reduceNoise
  (JNIEnv* env, jclass clazz,
   jstring jInput, jstring jOutput,
   jint kernelSize,
   jobject jResult)
{
    const char* input  = (*env)->GetStringUTFChars(env, jInput, NULL);
    const char* output = (*env)->GetStringUTFChars(env, jOutput, NULL);

    ProcessResult res = {0};

    int ok = reduce_noise(input, output, kernelSize, &res);

    fillJavaResult(env, jResult, &res);

    (*env)->ReleaseStringUTFChars(env, jInput, input);
    (*env)->ReleaseStringUTFChars(env, jOutput, output);

    return ok ? JNI_TRUE : JNI_FALSE;
}



// ------------------------------------------------------------------------------------
// JNI: openclCleanup
// ------------------------------------------------------------------------------------
JNIEXPORT void JNICALL Java_com_avicia_api_util_jni_image_ImageProcessorNative_openclCleanup
  (JNIEnv* env, jclass clazz)
{
    opencl_cleanup();
}
