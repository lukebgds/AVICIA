package com.avicia.api.util.jni.image;

public class ImageProcessorNative {

    static {
        try {

            // ======================================================
            // PASSO 1 — Forçar load do OpenCL REAL (opcional, seguro)
            // ======================================================
            try {
                System.out.println("[JNI] Carregando OpenCL REAL...");
                System.load("C:/Windows/System32/OpenCL.dll");
                System.out.println("[JNI] OpenCL.dll carregado com sucesso.");
            } catch (Throwable e) {
                System.err.println("[JNI] Aviso: não foi possível carregar OpenCL.dll manualmente: " + e.getMessage());
                // Não é erro fatal, continuamos normalmente
            }
        
            System.out.println("[JNI] Carregando image_processor.dll ...");

            System.load(
                "C:/Projects/AVICIA-PROJECT/AVICIA/api/src/main/resources/native/image_processor.dll"
            );

            System.out.println("[JNI] image_processor.dll carregada com sucesso.");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar bibliotecas nativas", e);
        }
    }

    // MATCH exato com ProcessResult do C
    public static class ProcessResult {
        public boolean success;
        public String message;
        public int width;
        public int height;
        public int channels;
    }

    // Métodos JNI
    public static native boolean openclInit();

    public static native boolean processImage(
            String inputPath,
            String outputPath,
            float contrast,
            float brightness,
            ProcessResult result
    );

    public static native boolean reduceNoise(
            String inputPath,
            String outputPath,
            int kernelSize,
            ProcessResult result
    );

    public static native void openclCleanup();
}
