package com.avicia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.object.PacienteDTO;
import com.avicia.api.service.PacienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public List<PacienteDTO> listarTodos() {
        return pacienteService.listarTodos();
    }

    @GetMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    public ResponseEntity<PacienteDTO> buscarPorCpf(@PathVariable String cpf) {
        return pacienteService.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping // localhost:9081/api/pacientes
    public PacienteDTO criar(@RequestBody PacienteDTO dto) {
        return pacienteService.criar(dto);
    }

    @PutMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    public ResponseEntity<PacienteDTO> atualizar(@PathVariable String cpf, @RequestBody PacienteDTO dto) {
        
        return pacienteService.atualizar(cpf, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        
        return pacienteService.deletar(cpf)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}
