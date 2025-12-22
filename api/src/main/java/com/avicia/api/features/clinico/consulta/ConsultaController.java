package com.avicia.api.features.clinico.consulta;

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

import com.avicia.api.data.enumerate.TipoConsulta;
import com.avicia.api.features.clinico.consulta.request.ConsultaRequest;
import com.avicia.api.features.clinico.consulta.response.ConsultaResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consultas")
@RequiredArgsConstructor
public class ConsultaController {

    private final ConsultaService consultaService;

    @GetMapping
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarTodos() {
        return consultaService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return consultaService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/ordenado")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorPacienteOrdenado(@PathVariable Integer idPaciente) {
        return consultaService.listarPorPacienteOrdenado(idPaciente);
    }

    @GetMapping("/profissional/{idProfissionalSaude}")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorProfissionalSaude(@PathVariable Integer idProfissionalSaude) {
        return consultaService.listarPorProfissionalSaude(idProfissionalSaude);
    }

    @GetMapping("/profissional/{idProfissionalSaude}/ordenado")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorProfissionalSaudeOrdenado(@PathVariable Integer idProfissionalSaude) {
        return consultaService.listarPorProfissionalSaudeOrdenado(idProfissionalSaude);
    }

    @GetMapping("/tipo")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorTipo(@RequestParam TipoConsulta tipoConsulta) {
        return consultaService.listarPorTipo(tipoConsulta);
    }

    @GetMapping("/paciente/{idPaciente}/tipo")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorPacienteETipo(
            @PathVariable Integer idPaciente,
            @RequestParam TipoConsulta tipoConsulta) {
        return consultaService.listarPorPacienteETipo(idPaciente, tipoConsulta);
    }

    @GetMapping("/profissional/{idProfissionalSaude}/tipo")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorProfissionalETipo(
            @PathVariable Integer idProfissionalSaude,
            @RequestParam TipoConsulta tipoConsulta) {
        return consultaService.listarPorProfissionalETipo(idProfissionalSaude, tipoConsulta);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return consultaService.listarPorPeriodo(dataInicio, dataFim);
    }

    @GetMapping("/paciente/{idPaciente}/periodo")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorPacienteEPeriodo(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return consultaService.listarPorPacienteEPeriodo(idPaciente, dataInicio, dataFim);
    }

    @GetMapping("/profissional/{idProfissionalSaude}/periodo")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> listarPorProfissionalEPeriodo(
            @PathVariable Integer idProfissionalSaude,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return consultaService.listarPorProfissionalEPeriodo(idProfissionalSaude, dataInicio, dataFim);
    }

    @GetMapping("/local")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public List<ConsultaResponse> buscarPorLocal(@RequestParam String localConsulta) {
        return consultaService.buscarPorLocal(localConsulta);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CONSULTA_READ')")
    public ResponseEntity<ConsultaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CONSULTA_CREATE')")
    public ConsultaResponse criar(@RequestBody ConsultaRequest dto) {
        return consultaService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CONSULTA_UPDATE')")
    public ResponseEntity<ConsultaResponse> atualizar(@PathVariable Integer id, @RequestBody ConsultaRequest dto) { 
        return ResponseEntity.ok(consultaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CONSULTA_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
