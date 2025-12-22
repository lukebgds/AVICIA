package com.avicia.api.features.paciente.dados.vacina;

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

import com.avicia.api.features.paciente.dados.vacina.request.PacienteVacinaRequest;
import com.avicia.api.features.paciente.dados.vacina.response.PacienteVacinaResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes/vacinas")
@RequiredArgsConstructor
public class PacienteVacinaController {

    private final PacienteVacinaService pacienteVacinaService;

    @GetMapping
    @PreAuthorize("hasAuthority('VACINA_READ')")
    public List<PacienteVacinaResponse> listarTodos() {
        return pacienteVacinaService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('VACINA_READ')")
    public List<PacienteVacinaResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return pacienteVacinaService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/ordenado")
    @PreAuthorize("hasAuthority('VACINA_READ')")
    public List<PacienteVacinaResponse> listarPorPacienteOrdenado(@PathVariable Integer idPaciente) {
        return pacienteVacinaService.listarPorPacienteOrdenado(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/buscar")
    @PreAuthorize("hasAuthority('VACINA_READ')")
    public List<PacienteVacinaResponse> buscarPorNomeVacina(
            @PathVariable Integer idPaciente,
            @RequestParam String vacina) {
        return pacienteVacinaService.buscarPorNomeVacina(idPaciente, vacina);
    }

    @GetMapping("/paciente/{idPaciente}/periodo")
    @PreAuthorize("hasAuthority('VACINA_READ')")
    public List<PacienteVacinaResponse> listarPorPeriodo(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim) {
        return pacienteVacinaService.listarPorPeriodo(idPaciente, dataInicio, dataFim);
    }

    @GetMapping("/paciente/{idPaciente}/recentes")
    @PreAuthorize("hasAuthority('VACINA_READ')")
    public List<PacienteVacinaResponse> listarVacinasRecentes(
            @PathVariable Integer idPaciente,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataReferencia) {
        return pacienteVacinaService.listarVacinasRecentes(idPaciente, dataReferencia);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('VACINA_READ')")
    public ResponseEntity<PacienteVacinaResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteVacinaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('VACINA_CREATE')")
    public PacienteVacinaResponse criar(@RequestBody PacienteVacinaRequest dto) {
        return pacienteVacinaService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('VACINA_UPDATE')")
    public ResponseEntity<PacienteVacinaResponse> atualizar(@PathVariable Integer id, @RequestBody PacienteVacinaRequest dto) { 
        return ResponseEntity.ok(pacienteVacinaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('VACINA_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pacienteVacinaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
