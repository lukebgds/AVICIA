package com.avicia.api.features.clinico.exame.solicitado;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.features.clinico.exame.solicitado.request.ExameSolicitadoRequest;
import com.avicia.api.features.clinico.exame.solicitado.response.ExameSolicitadoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/exames/solicitados")
@RequiredArgsConstructor
public class ExameSolicitadoController {

    private final ExameSolicitadoService exameSolicitadoService;

    @GetMapping
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarTodos() {
        return exameSolicitadoService.listarTodos();
    }

    @GetMapping("/consulta/{idConsulta}")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorConsulta(@PathVariable Integer idConsulta) {
        return exameSolicitadoService.listarPorConsulta(idConsulta);
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return exameSolicitadoService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/ordenado")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorPacienteOrdenado(@PathVariable Integer idPaciente) {
        return exameSolicitadoService.listarPorPacienteOrdenado(idPaciente);
    }

    @GetMapping("/profissional/{idProfissional}")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorProfissional(@PathVariable Integer idProfissional) {
        return exameSolicitadoService.listarPorProfissional(idProfissional);
    }

    @GetMapping("/tipo-exame/{idExame}")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorTipoExame(@PathVariable Integer idExame) {
        return exameSolicitadoService.listarPorTipoExame(idExame);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorStatus(@RequestParam String status) {
        return exameSolicitadoService.listarPorStatus(status);
    }

    @GetMapping("/paciente/{idPaciente}/status")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorPacienteEStatus(
            @PathVariable Integer idPaciente,
            @RequestParam String status) {
        return exameSolicitadoService.listarPorPacienteEStatus(idPaciente, status);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return exameSolicitadoService.listarPorPeriodo(dataInicio, dataFim);
    }

    @GetMapping("/paciente/{idPaciente}/periodo")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public List<ExameSolicitadoResponse> listarPorPacienteEPeriodo(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return exameSolicitadoService.listarPorPacienteEPeriodo(idPaciente, dataInicio, dataFim);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_READ')")
    public ResponseEntity<ExameSolicitadoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(exameSolicitadoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_CREATE')")
    public ExameSolicitadoResponse criar(@RequestBody ExameSolicitadoRequest dto) {
        return exameSolicitadoService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_UPDATE')")
    public ResponseEntity<ExameSolicitadoResponse> atualizar(
            @PathVariable Integer id, 
            @RequestBody ExameSolicitadoRequest dto) { 
        return ResponseEntity.ok(exameSolicitadoService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_UPDATE')")
    public ResponseEntity<ExameSolicitadoResponse> atualizarStatus(
            @PathVariable Integer id,
            @RequestParam String status) {
        return ResponseEntity.ok(exameSolicitadoService.atualizarStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMESOLICITADO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        exameSolicitadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
