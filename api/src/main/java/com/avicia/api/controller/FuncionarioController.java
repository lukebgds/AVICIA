package com.avicia.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.data.dto.object.FuncionarioDTO;
import com.avicia.api.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios")
@CrossOrigin(origins = "http://localhost:8080")
@RequiredArgsConstructor
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @PostMapping // localhost:9081/api/funcionarios
    public ResponseEntity<FuncionarioDTO> criar(@RequestBody FuncionarioDTO dto) {
        return ResponseEntity.ok(funcionarioService.criar(dto));
    }

    @GetMapping // localhost:9081/api/funcionarios
    public ResponseEntity<List<FuncionarioDTO>> listarTodos() {
        return ResponseEntity.ok(funcionarioService.listarTodos());
    }

    @GetMapping("/id/{idAdministrativo}") // localhost:9081/api/funcionarios/{idAdministrativo}
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Integer idAdministrativo) {
        
        return funcionarioService.buscarPorId(idAdministrativo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    public ResponseEntity<FuncionarioDTO> buscarPorMatricula(@PathVariable String matricula) {

        return funcionarioService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable String matricula, @RequestBody FuncionarioDTO dto) {

        return funcionarioService.atualizar(matricula, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}") // localhost:9081/api/funcionarios/{matricula}
    public ResponseEntity<Void> deletar(@PathVariable String matricula) {

        boolean deletado = funcionarioService.deletar(matricula);
        
        return deletado ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build();
    }

}
