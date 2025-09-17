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

import com.avicia.api.data.dto.object.FuncionarioDTO;
import com.avicia.api.service.FuncionarioService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios")
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

    @GetMapping("/{idAdministrativo}") // localhost:9081/api/funcionarios/{idAdministrativo}
    public ResponseEntity<FuncionarioDTO> buscarPorId(@PathVariable Integer idAdministrativo) {
        
        return funcionarioService.buscarPorId(idAdministrativo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula/{matricula}") // localhost:9081/api/funcionarios/matricula/{matricula}
    public ResponseEntity<FuncionarioDTO> buscarPorMatricula(@PathVariable String matricula) {

        return funcionarioService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{idAdministrativo}") // localhost:9081/api/funcionarios/{idAdministrativo}
    public ResponseEntity<FuncionarioDTO> atualizar(@PathVariable Integer idAdministrativo, @RequestBody FuncionarioDTO dto) {

        return funcionarioService.atualizar(idAdministrativo, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{idAdministrativo}") // localhost:9081/api/funcionarios/{idAdministrativo}
    public ResponseEntity<Void> deletar(@PathVariable Integer idAdministrativo) {

        boolean deletado = funcionarioService.deletar(idAdministrativo);
        
        return deletado ? ResponseEntity.noContent().build()
                        : ResponseEntity.notFound().build();
    }

}
