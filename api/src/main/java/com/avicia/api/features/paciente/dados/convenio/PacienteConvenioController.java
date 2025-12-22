package com.avicia.api.features.paciente.dados.convenio;

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

import com.avicia.api.features.paciente.dados.convenio.request.PacienteConvenioRequest;
import com.avicia.api.features.paciente.dados.convenio.response.PacienteConvenioResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes/convenios")
@RequiredArgsConstructor
public class PacienteConvenioController {

    private final PacienteConvenioService pacienteConvenioService;

    @GetMapping
    @PreAuthorize("hasAuthority('CONVENIO_READ')")
    public List<PacienteConvenioResponse> listarTodos() {
        return pacienteConvenioService.listarTodos();
    }

    @GetMapping("/paciente/{idPaciente}")
    @PreAuthorize("hasAuthority('CONVENIO_READ')")
    public List<PacienteConvenioResponse> listarPorPaciente(@PathVariable Integer idPaciente) {
        return pacienteConvenioService.listarPorPaciente(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/nome-convenio")
    @PreAuthorize("hasAuthority('CONVENIO_READ')")
    public List<PacienteConvenioResponse> listarPorPacienteENomeConvenio(
            @PathVariable Integer idPaciente,
            @RequestParam String nomeConvenio) {
        return pacienteConvenioService.listarPorPacienteENomeConvenio(idPaciente, nomeConvenio);
    }

    @GetMapping("/paciente/{idPaciente}/validos")
    @PreAuthorize("hasAuthority('CONVENIO_READ')")
    public List<PacienteConvenioResponse> listarConveniosValidos(@PathVariable Integer idPaciente) {
        return pacienteConvenioService.listarConveniosValidos(idPaciente);
    }

    @GetMapping("/paciente/{idPaciente}/vencidos")
    @PreAuthorize("hasAuthority('CONVENIO_READ')")
    public List<PacienteConvenioResponse> listarConveniosVencidos(@PathVariable Integer idPaciente) {
        return pacienteConvenioService.listarConveniosVencidos(idPaciente);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CONVENIO_READ')")
    public ResponseEntity<PacienteConvenioResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(pacienteConvenioService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CONVENIO_CREATE')")
    public PacienteConvenioResponse criar(@RequestBody PacienteConvenioRequest dto) {
        return pacienteConvenioService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CONVENIO_UPDATE')")
    public ResponseEntity<PacienteConvenioResponse> atualizar(@PathVariable Integer id, @RequestBody PacienteConvenioRequest dto) { 
        return ResponseEntity.ok(pacienteConvenioService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CONVENIO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        pacienteConvenioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
