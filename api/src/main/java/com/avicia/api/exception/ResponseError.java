package com.avicia.api.exception;

import java.util.Date;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ResponseError {

    private Date timesTamp = new Date();
    private String status = "error";
    private Integer statusCode = 400;
    private String erro;

}
