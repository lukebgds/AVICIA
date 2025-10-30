package com.avicia.api.features.associacao.paciente.usuario;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/usuarios-pacientes")
@RequiredArgsConstructor
public class PacienteUsuarioController {

    private final PacienteUsuarioService service;

    @PostMapping("/{idUsuario}/{idPaciente}")
    public ResponseEntity<Void> criar(@PathVariable Integer idUsuario, @PathVariable Integer idPaciente) {
        service.criarVinculo(idUsuario, idPaciente);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idUsuario}/{idPaciente}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idUsuario, @PathVariable Integer idPaciente) {
        service.deletarVinculo(idUsuario, idPaciente);
        return ResponseEntity.noContent().build();
    }

}
