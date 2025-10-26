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

import com.avicia.api.data.dto.request.paciente.PacienteRequest;
import com.avicia.api.data.dto.response.paciente.PacienteResponse;
import com.avicia.api.service.PacienteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;

    @GetMapping
    public List<PacienteResponse> listarTodos() {
        return pacienteService.listarTodos();
    }

    @GetMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    public ResponseEntity<PacienteResponse> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(pacienteService.buscarPorCpf(cpf));
    }

    @PostMapping("/cadastro") // localhost:9081/api/pacientes/cadastro
    public PacienteResponse criar(@RequestBody PacienteRequest dto) {
        return pacienteService.criar(dto);
    }

    @PutMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    public ResponseEntity<PacienteResponse> atualizar(@PathVariable String cpf, @RequestBody PacienteRequest dto) { 
        return ResponseEntity.ok(pacienteService.atualizar(cpf, dto));
    }

    @DeleteMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        pacienteService.deletar(cpf);
        return ResponseEntity.noContent().build();
    }
}
