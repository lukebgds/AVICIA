package com.avicia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.request.funcionario.FuncionarioRequest;
import com.avicia.api.data.dto.response.funcionario.FuncionarioResponse;
import com.avicia.api.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping // localhost:9081/api/funcionarios
    @PreAuthorize("hasAuthority('FUNCIONARIO_CREATE')")
    public ResponseEntity<FuncionarioResponse> criar(@RequestBody FuncionarioRequest dto) {
        return ResponseEntity.ok(funcionarioService.criar(dto));
    }

    @GetMapping // localhost:9081/api/funcionarios
    @PreAuthorize("hasAuthority('FUNCIONARIO_READ')")
    public ResponseEntity<List<FuncionarioResponse>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

    @GetMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    @PreAuthorize("hasAuthority('FUNCIONARIO_READ')")
    public ResponseEntity<FuncionarioResponse> buscarPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(funcionarioService.buscarPorMatricula(matricula));
    }

    @PutMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    @PreAuthorize("hasAuthority('FUNCIONARIO_UPDATE')")
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable String matricula, @RequestBody FuncionarioRequest dto) {
        return ResponseEntity.ok(funcionarioService.atualizar(matricula, dto));
    }

    @DeleteMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    @PreAuthorize("hasAuthority('FUNCIONARIO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable String matricula) {
        funcionarioService.deletar(matricula);
        return ResponseEntity.noContent().build();
                
    }

}
