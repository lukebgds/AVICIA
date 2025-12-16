package com.avicia.api.features.sistema.image;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/native/image-processor")
@RequiredArgsConstructor
public class ImageProcessorController {

    @PostMapping("/process")
    public ResponseEntity<?> process(@RequestBody ImageProcessRequest req) {

        // TESTE
        System.out.println("JAVA PATH = " + System.getenv("PATH"));

        // Inicializa OpenCL
        boolean ok = ImageProcessorNative.openclInit();
        if (!ok) {
            return ResponseEntity.status(500).body("Falha ao inicializar OpenCL");
        }

        ImageProcessorNative.ProcessResult result = new ImageProcessorNative.ProcessResult();

        boolean success = ImageProcessorNative.processImage(
                req.getInputPath(),
                req.getOutputPath(),
                req.getContrast(),
                req.getBrightness(),
                result
        );

        ImageProcessorNative.openclCleanup();

        if (!success) {
            return ResponseEntity.status(500)
                    .body("Falha ao processar imagem: " + result.message);
        }

        // Retorno mínimo para debug
        return ResponseEntity.ok(
                "Imagem processada com sucesso.\n" +
                "Saída: " + req.getOutputPath() + "\n" +
                "Dimensões: " + result.width + "x" + result.height + "\n" +
                "Canais: " + result.channels
        );
    }

}
