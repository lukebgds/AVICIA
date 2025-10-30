package com.avicia.api.features.agenda;

import java.time.LocalDateTime;
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

import com.avicia.api.data.enumerate.StatusAgenda;
import com.avicia.api.features.agenda.request.AgendaRequest;
import com.avicia.api.features.agenda.response.AgendaResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/agendas")
@RequiredArgsConstructor
public class AgendaController {

    private final AgendaService agendaService;

    @GetMapping
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarTodos() {
        return agendaService.listarTodos();
    }

    @GetMapping("/profissional/{idProfissionalSaude}")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorProfissionalSaude(@PathVariable Integer idProfissionalSaude) {
        return agendaService.listarPorProfissionalSaude(idProfissionalSaude);
    }

    @GetMapping("/profissional/{idProfissionalSaude}/ordenado")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorProfissionalSaudeOrdenado(@PathVariable Integer idProfissionalSaude) {
        return agendaService.listarPorProfissionalSaudeOrdenado(idProfissionalSaude);
    }

    @GetMapping("/profissional/{idProfissionalSaude}/futuras")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarAgendasFuturasProfissional(@PathVariable Integer idProfissionalSaude) {
        return agendaService.listarAgendasFuturasProfissional(idProfissionalSaude);
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return agendaService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/ordenado")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorPacienteOrdenado(@PathVariable Integer idPaciente) {
        return agendaService.listarPorPacienteOrdenado(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/futuras")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarAgendasFuturasPaciente(@PathVariable Integer idPaciente) {
        return agendaService.listarAgendasFuturasPaciente(idPaciente);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorStatus(@RequestParam StatusAgenda status) {
        return agendaService.listarPorStatus(status);
    }

    @GetMapping("/profissional/{idProfissionalSaude}/status")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorProfissionalEStatus(
            @PathVariable Integer idProfissionalSaude,
            @RequestParam StatusAgenda status) {
        return agendaService.listarPorProfissionalEStatus(idProfissionalSaude, status);
    }

    @GetMapping("/paciente/{idPaciente}/status")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorPacienteEStatus(
            @PathVariable Integer idPaciente,
            @RequestParam StatusAgenda status) {
        return agendaService.listarPorPacienteEStatus(idPaciente, status);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return agendaService.listarPorPeriodo(dataInicio, dataFim);
    }

    @GetMapping("/profissional/{idProfissionalSaude}/periodo")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorProfissionalEPeriodo(
            @PathVariable Integer idProfissionalSaude,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return agendaService.listarPorProfissionalEPeriodo(idProfissionalSaude, dataInicio, dataFim);
    }

    @GetMapping("/paciente/{idPaciente}/periodo")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public List<AgendaResponse> listarPorPacienteEPeriodo(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return agendaService.listarPorPacienteEPeriodo(idPaciente, dataInicio, dataFim);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('AGENDA_READ')")
    public ResponseEntity<AgendaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(agendaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('AGENDA_CREATE')")
    public AgendaResponse criar(@RequestBody AgendaRequest dto) {
        return agendaService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('AGENDA_UPDATE')")
    public ResponseEntity<AgendaResponse> atualizar(@PathVariable Integer id, @RequestBody AgendaRequest dto) { 
        return ResponseEntity.ok(agendaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('AGENDA_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        agendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
