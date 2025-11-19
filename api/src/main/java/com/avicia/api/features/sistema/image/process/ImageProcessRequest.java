package com.avicia.api.features.sistema.image.process;

import lombok.Data;

@Data
public class ImageProcessRequest {
    
    private String inputPath;
    private String outputPath;
    private float contrast;
    private float brightness;
    
}
