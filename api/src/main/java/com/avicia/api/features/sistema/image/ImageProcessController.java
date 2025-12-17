package com.avicia.api.features.sistema.image;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageProcessController {

    private final ImageProcessService imageProcessService;

    @PostMapping("/process")
    public ResponseEntity<ImageProcessResponse> processImage(
            @Valid @RequestBody ImageProcessRequest request) {
        
        log.info("Recebida requisição para processar imagem: {}", request.getInputPath());
        
        try {
            ImageProcessResponse response = imageProcessService.processImage(request);
            return ResponseEntity.ok(response);
            
        } catch (IllegalArgumentException e) {
            log.warn("Requisição inválida: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
            
        } catch (ImageProcessException e) {
            log.error("Erro no processamento da imagem", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
