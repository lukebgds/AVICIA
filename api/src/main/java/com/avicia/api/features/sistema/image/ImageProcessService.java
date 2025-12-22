package com.avicia.api.features.sistema.image;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ImageProcessService {

    private static final String NATIVE_DIR = "src/main/native";
    private static final String OUTPUT_DIR = NATIVE_DIR + "/output";
    private static final String EXECUTABLE = NATIVE_DIR + "/image_processor.exe";

    public ImageProcessService() {
        // Cria o diretório de output se não existir
        try {
            Path outputPath = Paths.get(OUTPUT_DIR);
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
                log.info("Diretório de output criado: {}", OUTPUT_DIR);
            }
        } catch (IOException e) {
            log.error("Erro ao criar diretório de output", e);
        }
    }

    public ImageProcessResponse processImage(ImageProcessRequest request) {
        validateRequest(request);
        
        // Gera nome único para o arquivo de output
        String outputFileName = generateOutputFileName(request.getInputPath());
        String outputPath = OUTPUT_DIR + "/" + outputFileName;

        // Converte valores de porcentagem (-100 a 100) para valores float esperados pelo exe
        float contrast = convertContrastToFloat(request.getContrast());
        float brightness = convertBrightnessToFloat(request.getBrightness());
        float saturation = convertSaturationToFloat(request.getSaturation());

        log.info("Processando imagem: input={}, contrast={}, brightness={}, saturation={}", 
                 request.getInputPath(), contrast, brightness, saturation);

        try {
            executeImageProcessor(request.getInputPath(), outputPath, contrast, brightness, saturation);
            
            // Converte para caminho absoluto
            File outputFile = new File(outputPath);
            String absolutePath = outputFile.getAbsolutePath().replace("\\", "/");
            
            ImageProcessResponse response = new ImageProcessResponse();
            response.setOutputPath(absolutePath);
            
            log.info("Imagem processada com sucesso: {}", absolutePath);
            return response;
            
        } catch (Exception e) {
            log.error("Erro ao processar imagem", e);
            throw new ImageProcessException("Falha ao processar imagem: " + e.getMessage(), e);
        }
    }

    private void validateRequest(ImageProcessRequest request) {
        if (request.getInputPath() == null || request.getInputPath().trim().isEmpty()) {
            throw new IllegalArgumentException("Input path não pode ser vazio");
        }

        File inputFile = new File(request.getInputPath());
        if (!inputFile.exists()) {
            throw new IllegalArgumentException("Arquivo de entrada não encontrado: " + request.getInputPath());
        }

        if (!inputFile.isFile()) {
            throw new IllegalArgumentException("Input path deve ser um arquivo válido");
        }

        // Valida ranges dos parâmetros
        validateRange(request.getContrast(), "Contrast");
        validateRange(request.getBrightness(), "Brightness");
        validateRange(request.getSaturation(), "Saturation");
    }

    private void validateRange(int value, String paramName) {
        if (value < -100 || value > 100) {
            throw new IllegalArgumentException(
                paramName + " deve estar entre -100 e 100. Valor recebido: " + value
            );
        }
    }

    /**
     * Converte contrast de porcentagem (-100 a 100) para float
     * -100% = 0.0 (sem contraste)
     *    0% = 1.0 (contraste normal)
     * +100% = 2.0 (contraste máximo)
     */
    private float convertContrastToFloat(int contrastPercent) {
        float x = 1.0f + (contrastPercent / 100.0f);
        System.out.println(x);
        return x;
    }

    /**
     * Converte brightness de porcentagem (-100 a 100) para float
     * -100% = -1.0 (muito escuro)
     *    0% = 0.0 (brilho normal)
     * +100% = 1.0 (muito claro)
     */
    private float convertBrightnessToFloat(int brightnessPercent) {
        float x = brightnessPercent / 100.0f;
        System.out.println(x);
        return x;
    }

    /**
     * Converte saturation de porcentagem (-100 a 100) para float
     * -100% = 0.0 (preto e branco)
     *    0% = 1.0 (saturação normal)
     * +100% = 2.0 (saturação máxima)
     */
    private float convertSaturationToFloat(int saturationPercent) {
        float x = 1.0f + (saturationPercent / 100.0f);
        return x;
    }

    private String generateOutputFileName(String inputPath) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
        
        // Extrai o nome do arquivo original sem extensão
        File inputFile = new File(inputPath);
        String originalFileName = inputFile.getName();
        int lastDotIndex = originalFileName.lastIndexOf('.');
        String baseFileName = lastDotIndex > 0 
            ? originalFileName.substring(0, lastDotIndex) 
            : originalFileName;
        
        String extension = getFileExtension(inputPath);
        return baseFileName + "_processed_" + timestamp + "." + extension;
    }

    private String getFileExtension(String filePath) {
        int lastDotIndex = filePath.lastIndexOf('.');
        if (lastDotIndex > 0 && lastDotIndex < filePath.length() - 1) {
            return filePath.substring(lastDotIndex + 1);
        }
        return "png"; // extensão padrão
    }

    private void executeImageProcessor(String inputPath, String outputPath, 
                                       float contrast, float brightness, float saturation) 
            throws IOException, InterruptedException {
        
        File executable = new File(EXECUTABLE);
        if (!executable.exists()) {
            throw new IllegalStateException("Executável não encontrado: " + EXECUTABLE);
        }

        List<String> command = new ArrayList<>();
        command.add(executable.getAbsolutePath());
        command.add(inputPath);
        command.add(outputPath);
        command.add(String.format(Locale.US, "%.2f", contrast));
        command.add(String.format(Locale.US, "%.2f", brightness));
        command.add(String.format(Locale.US, "%.2f", saturation));

        log.debug("Executando comando: {}", String.join(" ", command));

        System.out.println(command);

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        
        Process process = processBuilder.start();

        // Captura e loga a saída do processo
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
                log.debug("Processo: {}", line);
            }
        }

        int exitCode = process.waitFor();
        
        if (exitCode != 0) {
            log.error("Processo falhou com código {}: {}", exitCode, output.toString());
            throw new ImageProcessException(
                "Processamento falhou com código " + exitCode + ": " + output.toString()
            );
        }

        // Verifica se o arquivo de output foi criado
        File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            throw new ImageProcessException(
                "Arquivo de output não foi criado: " + outputPath
            );
        }

        log.info("Processo concluído com sucesso. Output: {}", output.toString());
    }

}
