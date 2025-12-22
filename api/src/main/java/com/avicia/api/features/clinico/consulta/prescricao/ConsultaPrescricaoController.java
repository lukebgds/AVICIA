package com.avicia.api.features.clinico.consulta.prescricao;

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

import com.avicia.api.data.enumerate.StatusPrescricao;
import com.avicia.api.features.clinico.consulta.prescricao.request.ConsultaPrescricaoRequest;
import com.avicia.api.features.clinico.consulta.prescricao.response.ConsultaPrescricaoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consultas/prescricoes")
@RequiredArgsConstructor
public class ConsultaPrescricaoController {

    private final ConsultaPrescricaoService consultaPrescricaoService;

    @GetMapping
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<ConsultaPrescricaoResponse> listarTodos() {
        return consultaPrescricaoService.listarTodos();
    }

    @GetMapping("/consulta/{idConsulta}")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<ConsultaPrescricaoResponse> listarPorConsulta(@PathVariable Integer idConsulta) {
        return consultaPrescricaoService.listarPorConsulta(idConsulta);
    }

    @GetMapping("/consulta/{idConsulta}/ordenado")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<ConsultaPrescricaoResponse> listarPorConsultaOrdenado(@PathVariable Integer idConsulta) {
        return consultaPrescricaoService.listarPorConsultaOrdenado(idConsulta);
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<ConsultaPrescricaoResponse> listarPorStatus(@RequestParam StatusPrescricao status) {
        return consultaPrescricaoService.listarPorStatus(status);
    }

    @GetMapping("/consulta/{idConsulta}/status")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<ConsultaPrescricaoResponse> listarPorConsultaEStatus(
            @PathVariable Integer idConsulta,
            @RequestParam StatusPrescricao status) {
        return consultaPrescricaoService.listarPorConsultaEStatus(idConsulta, status);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<ConsultaPrescricaoResponse> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return consultaPrescricaoService.listarPorPeriodo(dataInicio, dataFim);
    }

    @GetMapping("/consulta/{idConsulta}/periodo")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<ConsultaPrescricaoResponse> listarPorConsultaEPeriodo(
            @PathVariable Integer idConsulta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return consultaPrescricaoService.listarPorConsultaEPeriodo(idConsulta, dataInicio, dataFim);
    }

    @GetMapping("/recentes")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public List<ConsultaPrescricaoResponse> listarPrescricoesRecentes(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataReferencia) {
        return consultaPrescricaoService.listarPrescricoesRecentes(dataReferencia);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('PRESCRICAO_READ')")
    public ResponseEntity<ConsultaPrescricaoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaPrescricaoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('PRESCRICAO_CREATE')")
    public ConsultaPrescricaoResponse criar(@RequestBody ConsultaPrescricaoRequest dto) {
        return consultaPrescricaoService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('PRESCRICAO_UPDATE')")
    public ResponseEntity<ConsultaPrescricaoResponse> atualizar(@PathVariable Integer id, @RequestBody ConsultaPrescricaoRequest dto) { 
        return ResponseEntity.ok(consultaPrescricaoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('PRESCRICAO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        consultaPrescricaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
