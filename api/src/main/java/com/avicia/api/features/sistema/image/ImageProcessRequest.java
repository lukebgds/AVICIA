package com.avicia.api.features.sistema.image;

import lombok.Data;

@Data
public class ImageProcessRequest {
    
    private String inputPath;
    private float contrast;
    private float brightness;
    private float saturation;
    
}
