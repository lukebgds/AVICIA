package com.avicia.api.features.clinico.exame.resultado;

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

import com.avicia.api.features.clinico.exame.resultado.request.ExameResultadoRequest;
import com.avicia.api.features.clinico.exame.resultado.response.ExameResultadoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/exames/resultados")
@RequiredArgsConstructor
public class ExameResultadoController {

    private final ExameResultadoService exameResultadoService;

    @GetMapping
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public List<ExameResultadoResponse> listarTodos() {
        return exameResultadoService.listarTodos();
    }

    @GetMapping("/exame-solicitado/{idExameSolicitado}")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public ResponseEntity<ExameResultadoResponse> buscarPorExameSolicitado(@PathVariable Integer idExameSolicitado) {
        ExameResultadoResponse resultado = exameResultadoService.buscarPorExameSolicitado(idExameSolicitado);
        if (resultado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public List<ExameResultadoResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return exameResultadoService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/ordenado")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public List<ExameResultadoResponse> listarPorPacienteOrdenado(@PathVariable Integer idPaciente) {
        return exameResultadoService.listarPorPacienteOrdenado(idPaciente);
    }

    @GetMapping("/profissional/{idProfissional}")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public List<ExameResultadoResponse> listarPorProfissional(@PathVariable Integer idProfissional) {
        return exameResultadoService.listarPorProfissional(idProfissional);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public List<ExameResultadoResponse> listarPorStatus(@RequestParam String status) {
        return exameResultadoService.listarPorStatus(status);
    }

    @GetMapping("/paciente/{idPaciente}/status")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public List<ExameResultadoResponse> listarPorPacienteEStatus(
            @PathVariable Integer idPaciente,
            @RequestParam String status) {
        return exameResultadoService.listarPorPacienteEStatus(idPaciente, status);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public List<ExameResultadoResponse> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return exameResultadoService.listarPorPeriodo(dataInicio, dataFim);
    }

    @GetMapping("/pendentes-assinatura")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public List<ExameResultadoResponse> listarPendentesAssinatura() {
        return exameResultadoService.listarPendentesAssinatura();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_READ')")
    public ResponseEntity<ExameResultadoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(exameResultadoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EXAMERESULTADO_CREATE')")
    public ExameResultadoResponse criar(@RequestBody ExameResultadoRequest dto) {
        return exameResultadoService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_UPDATE')")
    public ResponseEntity<ExameResultadoResponse> atualizar(
            @PathVariable Integer id, 
            @RequestBody ExameResultadoRequest dto) { 
        return ResponseEntity.ok(exameResultadoService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/assinar")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_UPDATE')")
    public ResponseEntity<ExameResultadoResponse> assinar(
            @PathVariable Integer id,
            @RequestParam Integer idProfissional) {
        return ResponseEntity.ok(exameResultadoService.assinar(id, idProfissional));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAMERESULTADO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        exameResultadoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
