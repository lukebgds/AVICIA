package com.avicia.api.features.paciente.dados.alergia;

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

import com.avicia.api.features.paciente.dados.alergia.request.PacienteAlergiaRequest;
import com.avicia.api.features.paciente.dados.alergia.response.PacienteAlergiaResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes/alergias")
@RequiredArgsConstructor
public class PacienteAlergiaController {

    private final PacienteAlergiaService pacienteAlergiaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ALERGIA_READ')")
    public List<PacienteAlergiaResponse> listarTodos() {
        return pacienteAlergiaService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('ALERGIA_READ')")
    public List<PacienteAlergiaResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return pacienteAlergiaService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ALERGIA_READ')")
    public ResponseEntity<PacienteAlergiaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteAlergiaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ALERGIA_CREATE')")
    public PacienteAlergiaResponse criar(@RequestBody PacienteAlergiaRequest dto) {
        return pacienteAlergiaService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ALERGIA_UPDATE')")
    public ResponseEntity<PacienteAlergiaResponse> atualizar(@PathVariable Integer id, @RequestBody PacienteAlergiaRequest dto) { 
        return ResponseEntity.ok(pacienteAlergiaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ALERGIA_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pacienteAlergiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
