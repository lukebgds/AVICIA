package com.avicia.api.features.paciente.dados.anexo;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.features.paciente.dados.anexo.request.PacienteAnexoRequest;
import com.avicia.api.features.paciente.dados.anexo.response.PacienteAnexoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes/anexos")
@RequiredArgsConstructor
public class PacienteAnexoController {

    private final PacienteAnexoService pacienteAnexoService;

    @GetMapping
    @PreAuthorize("hasAuthority('ANEXO_READ')")
    public List<PacienteAnexoResponse> listarTodos() {
        return pacienteAnexoService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('ANEXO_READ')")
    public List<PacienteAnexoResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return pacienteAnexoService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/tipo")
    @PreAuthorize("hasAuthority('ANEXO_READ')")
    public List<PacienteAnexoResponse> listarPorPacienteETipo(
            @PathVariable Integer idPaciente,
            @RequestParam String tipo) {
        return pacienteAnexoService.listarPorPacienteETipo(idPaciente, tipo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ANEXO_READ')")
    public ResponseEntity<PacienteAnexoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteAnexoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ANEXO_CREATE')")
    public PacienteAnexoResponse criar(@RequestBody PacienteAnexoRequest dto) {
        return pacienteAnexoService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ANEXO_UPDATE')")
    public ResponseEntity<PacienteAnexoResponse> atualizar(@PathVariable Integer id, @RequestBody PacienteAnexoRequest dto) { 
        return ResponseEntity.ok(pacienteAnexoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ANEXO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pacienteAnexoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
