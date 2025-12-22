package com.avicia.api.features.paciente.dados.antecedente;

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

import com.avicia.api.features.paciente.dados.antecedente.request.PacienteAntecedenteRequest;
import com.avicia.api.features.paciente.dados.antecedente.response.PacienteAntecedenteResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes/antecedentes")
@RequiredArgsConstructor
public class PacienteAntecedenteController {

    private final PacienteAntecedenteService pacienteAntecedenteService;

    @GetMapping
    @PreAuthorize("hasAuthority('ANTECEDENTE_READ')")
    public List<PacienteAntecedenteResponse> listarTodos() {
        return pacienteAntecedenteService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('ANTECEDENTE_READ')")
    public List<PacienteAntecedenteResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return pacienteAntecedenteService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/tipo-doenca")
    @PreAuthorize("hasAuthority('ANTECEDENTE_READ')")
    public List<PacienteAntecedenteResponse> listarPorPacienteETipoDoenca(
            @PathVariable Integer idPaciente,
            @RequestParam String tipoDoenca) {
        return pacienteAntecedenteService.listarPorPacienteETipoDoenca(idPaciente, tipoDoenca);
    }

    @GetMapping("/paciente/{idPaciente}/parentesco")
    @PreAuthorize("hasAuthority('ANTECEDENTE_READ')")
    public List<PacienteAntecedenteResponse> listarPorPacienteEParentesco(
            @PathVariable Integer idPaciente,
            @RequestParam String parentesco) {
        return pacienteAntecedenteService.listarPorPacienteEParentesco(idPaciente, parentesco);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ANTECEDENTE_READ')")
    public ResponseEntity<PacienteAntecedenteResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteAntecedenteService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ANTECEDENTE_CREATE')")
    public PacienteAntecedenteResponse criar(@RequestBody PacienteAntecedenteRequest dto) {
        return pacienteAntecedenteService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ANTECEDENTE_UPDATE')")
    public ResponseEntity<PacienteAntecedenteResponse> atualizar(@PathVariable Integer id, @RequestBody PacienteAntecedenteRequest dto) { 
        return ResponseEntity.ok(pacienteAntecedenteService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ANTECEDENTE_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pacienteAntecedenteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
