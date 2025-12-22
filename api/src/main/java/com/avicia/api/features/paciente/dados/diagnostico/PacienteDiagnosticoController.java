package com.avicia.api.features.paciente.dados.diagnostico;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import com.avicia.api.data.enumerate.StatusDiagnostico;
import com.avicia.api.data.enumerate.TipoDiagnostico;
import com.avicia.api.features.paciente.dados.diagnostico.response.PacienteDiagnosticoResponse;
import com.avicia.api.features.paciente.dados.diagnostico.resquest.PacienteDiagnosticoRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes/diagnosticos")
@RequiredArgsConstructor
public class PacienteDiagnosticoController {

    private final PacienteDiagnosticoService pacienteDiagnosticoService;

    @GetMapping
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<PacienteDiagnosticoResponse> listarTodos() {
        return pacienteDiagnosticoService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<PacienteDiagnosticoResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return pacienteDiagnosticoService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/codigo-cid")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<PacienteDiagnosticoResponse> listarPorPacienteECodigoCid(
            @PathVariable Integer idPaciente,
            @RequestParam String codigoCidDez) {
        return pacienteDiagnosticoService.listarPorPacienteECodigoCid(idPaciente, codigoCidDez);
    }

    @GetMapping("/paciente/{idPaciente}/tipo")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<PacienteDiagnosticoResponse> listarPorPacienteETipo(
            @PathVariable Integer idPaciente,
            @RequestParam TipoDiagnostico tipo) {
        return pacienteDiagnosticoService.listarPorPacienteETipo(idPaciente, tipo);
    }

    @GetMapping("/paciente/{idPaciente}/status")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<PacienteDiagnosticoResponse> listarPorPacienteEStatus(
            @PathVariable Integer idPaciente,
            @RequestParam StatusDiagnostico status) {
        return pacienteDiagnosticoService.listarPorPacienteEStatus(idPaciente, status);
    }

    @GetMapping("/paciente/{idPaciente}/periodo")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<PacienteDiagnosticoResponse> listarPorPacienteEPeriodo(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return pacienteDiagnosticoService.listarPorPacienteEPeriodo(idPaciente, dataInicio, dataFim);
    }

    @GetMapping("/paciente/{idPaciente}/tipo-status")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<PacienteDiagnosticoResponse> listarPorPacienteTipoEStatus(
            @PathVariable Integer idPaciente,
            @RequestParam TipoDiagnostico tipo,
            @RequestParam StatusDiagnostico status) {
        return pacienteDiagnosticoService.listarPorPacienteTipoEStatus(idPaciente, tipo, status);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public ResponseEntity<PacienteDiagnosticoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteDiagnosticoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('DIAGNOSTICO_CREATE')")
    public PacienteDiagnosticoResponse criar(@RequestBody PacienteDiagnosticoRequest dto) {
        return pacienteDiagnosticoService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_UPDATE')")
    public ResponseEntity<PacienteDiagnosticoResponse> atualizar(@PathVariable Integer id, @RequestBody PacienteDiagnosticoRequest dto) { 
        return ResponseEntity.ok(pacienteDiagnosticoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pacienteDiagnosticoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
