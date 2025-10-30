package com.avicia.api.features.associacao.paciente.funcionario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/funcionarios-pacientes")
@RequiredArgsConstructor
public class PacienteFuncionarioController {

    private final PacienteFuncionarioService service;

    @PostMapping("/{idFuncionario}/{idPaciente}")
    public ResponseEntity<Void> criar(@PathVariable Integer idFuncionario, @PathVariable Integer idPaciente) {
        service.criarVinculo(idFuncionario, idPaciente);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idFuncionario}/{idPaciente}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idFuncionario, @PathVariable Integer idPaciente) {
        service.deletarVinculo(idFuncionario, idPaciente);
        return ResponseEntity.noContent().build();
    }

}
