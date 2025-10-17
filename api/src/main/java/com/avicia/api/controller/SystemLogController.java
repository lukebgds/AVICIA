package com.avicia.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.response.system.SystemLogResponse;
import com.avicia.api.service.SystemLogService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/logs")
@RequiredArgsConstructor
public class SystemLogController {

    private final SystemLogService systemLogService;
    
    @GetMapping // localhost:9081/api/logs
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<List<SystemLogResponse>> listarTodos() {
        return ResponseEntity.ok(systemLogService.listarTodos());
    }

    @GetMapping("/{id}") // localhost:9081/api/logs/{id}
    @PreAuthorize("hasAuthority('LOG_READ')")
    public ResponseEntity<SystemLogResponse> buscarPorId(@PathVariable Integer id) {
        return systemLogService.buscarPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

}
