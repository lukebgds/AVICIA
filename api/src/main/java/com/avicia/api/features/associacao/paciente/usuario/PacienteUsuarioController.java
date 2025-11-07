package com.avicia.api.features.associacao.paciente.usuario;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
    private final PacienteUsuarioMapper mapper;

    @PostMapping("/{idUsuario}/{idPaciente}")
    @PreAuthorize("hasAuthority('ASSOCIACAO_CREATE')")
    public ResponseEntity<Void> criar(@PathVariable Integer idUsuario, @PathVariable Integer idPaciente) {
        service.criarVinculo(idUsuario, idPaciente);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{idUsuario}/{idPaciente}")
    @PreAuthorize("hasAuthority('ASSOCIACAO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer idUsuario, @PathVariable Integer idPaciente) {
        service.deletarVinculo(idUsuario, idPaciente);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ASSOCIACAO_READ')")
    public ResponseEntity<List<PacienteUsuarioResponse>> listarTodosVinculos() {
        List<PacienteUsuario> vinculos = service.listarTodosVinculos();
        return ResponseEntity.ok(mapper.toDTOList(vinculos));
    } 

}
