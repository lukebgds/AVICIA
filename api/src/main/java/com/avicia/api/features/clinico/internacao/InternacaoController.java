package com.avicia.api.features.clinico.internacao;

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

import com.avicia.api.features.clinico.internacao.request.InternacaoRequest;
import com.avicia.api.features.clinico.internacao.response.InternacaoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/internacoes")
@RequiredArgsConstructor
public class InternacaoController {

    private final InternacaoService internacaoService;

    @GetMapping
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> listarTodos() {
        return internacaoService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return internacaoService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/ordenado")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> listarPorPacienteOrdenado(@PathVariable Integer idPaciente) {
        return internacaoService.listarPorPacienteOrdenado(idPaciente);
    }

    @GetMapping("/profissional/{idProfissional}")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> listarPorProfissional(@PathVariable Integer idProfissional) {
        return internacaoService.listarPorProfissional(idProfissional);
    }

    @GetMapping("/ativas")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> listarInternacoesAtivas() {
        return internacaoService.listarInternacoesAtivas();
    }

    @GetMapping("/paciente/{idPaciente}/ativas")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> listarInternacoesAtivasPorPaciente(@PathVariable Integer idPaciente) {
        return internacaoService.listarInternacoesAtivasPorPaciente(idPaciente);
    }

    @GetMapping("/finalizadas")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> listarInternacoesFinalizadas() {
        return internacaoService.listarInternacoesFinalizadas();
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim) {
        return internacaoService.listarPorPeriodo(dataInicio, dataFim);
    }

    @GetMapping("/leito")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> buscarPorLeito(@RequestParam String leito) {
        return internacaoService.buscarPorLeito(leito);
    }

    @GetMapping("/leito/ativo")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public List<InternacaoResponse> buscarLeitoAtivo(@RequestParam String leito) {
        return internacaoService.buscarLeitoAtivo(leito);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('INTERNACAO_READ')")
    public ResponseEntity<InternacaoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(internacaoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('INTERNACAO_CREATE')")
    public InternacaoResponse criar(@RequestBody InternacaoRequest dto) {
        return internacaoService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('INTERNACAO_UPDATE')")
    public ResponseEntity<InternacaoResponse> atualizar(@PathVariable Integer id, @RequestBody InternacaoRequest dto) { 
        return ResponseEntity.ok(internacaoService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/alta")
    @PreAuthorize("hasAuthority('INTERNACAO_UPDATE')")
    public ResponseEntity<InternacaoResponse> registrarAlta(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataAlta) {
        return ResponseEntity.ok(internacaoService.registrarAlta(id, dataAlta));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('INTERNACAO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        internacaoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
