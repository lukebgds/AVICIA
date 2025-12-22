package com.avicia.api.features.sistema.image;

import lombok.Data;

@Data
public class ImageProcessRequest {
    
    private String inputPath;
    private int contrast;
    private int brightness;
    private int saturation;
    
}
