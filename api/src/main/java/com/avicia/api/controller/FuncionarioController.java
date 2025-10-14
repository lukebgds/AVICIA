package com.avicia.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.request.FuncionarioRequest;
import com.avicia.api.data.dto.response.FuncionarioResponse;
import com.avicia.api.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios")
@RequiredArgsConstructor
public class FuncionarioController {

    @Autowired
    private final FuncionarioService funcionarioService;

    @PostMapping // localhost:9081/api/funcionarios
    public ResponseEntity<FuncionarioResponse> criar(@RequestBody FuncionarioRequest dto) {
        return ResponseEntity.ok(funcionarioService.criar(dto));
    }

    @GetMapping // localhost:9081/api/funcionarios
    public ResponseEntity<List<FuncionarioResponse>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

    @GetMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    public ResponseEntity<FuncionarioResponse> buscarPorMatricula(@PathVariable String matricula) {

        return funcionarioService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    public ResponseEntity<FuncionarioResponse> atualizar(@PathVariable String matricula, @RequestBody FuncionarioRequest dto) {

        return funcionarioService.atualizar(matricula, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    public ResponseEntity<Void> deletar(@PathVariable String matricula) {

        return funcionarioService.deletar(matricula)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}
