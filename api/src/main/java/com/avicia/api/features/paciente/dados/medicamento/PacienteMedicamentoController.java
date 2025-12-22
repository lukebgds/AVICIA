package com.avicia.api.features.paciente.dados.medicamento;

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

import com.avicia.api.features.paciente.dados.medicamento.request.PacienteMedicamentoRequest;
import com.avicia.api.features.paciente.dados.medicamento.response.PacienteMedicamentoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes/medicamentos")
@RequiredArgsConstructor
public class PacienteMedicamentoController {

    private final PacienteMedicamentoService pacienteMedicamentoService;

    @GetMapping
    @PreAuthorize("hasAuthority('MEDICAMENTO_READ')")
    public List<PacienteMedicamentoResponse> listarTodos() {
        return pacienteMedicamentoService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('MEDICAMENTO_READ')")
    public List<PacienteMedicamentoResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return pacienteMedicamentoService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/buscar")
    @PreAuthorize("hasAuthority('MEDICAMENTO_READ')")
    public List<PacienteMedicamentoResponse> buscarPorNomeMedicamento(
            @PathVariable Integer idPaciente,
            @RequestParam String medicamento) {
        return pacienteMedicamentoService.buscarPorNomeMedicamento(idPaciente, medicamento);
    }

    @GetMapping("/paciente/{idPaciente}/frequencia")
    @PreAuthorize("hasAuthority('MEDICAMENTO_READ')")
    public List<PacienteMedicamentoResponse> listarPorFrequencia(
            @PathVariable Integer idPaciente,
            @RequestParam String frequencia) {
        return pacienteMedicamentoService.listarPorFrequencia(idPaciente, frequencia);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAMENTO_READ')")
    public ResponseEntity<PacienteMedicamentoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteMedicamentoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('MEDICAMENTO_CREATE')")
    public PacienteMedicamentoResponse criar(@RequestBody PacienteMedicamentoRequest dto) {
        return pacienteMedicamentoService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAMENTO_UPDATE')")
    public ResponseEntity<PacienteMedicamentoResponse> atualizar(@PathVariable Integer id, @RequestBody PacienteMedicamentoRequest dto) { 
        return ResponseEntity.ok(pacienteMedicamentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MEDICAMENTO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pacienteMedicamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
