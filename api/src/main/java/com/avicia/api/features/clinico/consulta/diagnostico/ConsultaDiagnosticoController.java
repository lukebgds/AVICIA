package com.avicia.api.features.clinico.consulta.diagnostico;

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

import com.avicia.api.features.clinico.consulta.diagnostico.request.ConsultaDiagnosticoRequest;
import com.avicia.api.features.clinico.consulta.diagnostico.response.ConsultaDiagnosticoResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/consultas/diagnosticos")
@RequiredArgsConstructor
public class ConsultaDiagnosticoController {

    private final ConsultaDiagnosticoService consultaDiagnosticoService;

    @GetMapping
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<ConsultaDiagnosticoResponse> listarTodos() {
        return consultaDiagnosticoService.listarTodos();
    }

    @GetMapping("/consulta/{idConsulta}")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<ConsultaDiagnosticoResponse> listarPorConsulta(@PathVariable Integer idConsulta) {
        return consultaDiagnosticoService.listarPorConsulta(idConsulta);
    }

    @GetMapping("/codigo-cid")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<ConsultaDiagnosticoResponse> listarPorCodigoCid(@RequestParam String codigoCidDez) {
        return consultaDiagnosticoService.listarPorCodigoCid(codigoCidDez);
    }

    @GetMapping("/consulta/{idConsulta}/codigo-cid")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<ConsultaDiagnosticoResponse> listarPorConsultaECodigoCid(
            @PathVariable Integer idConsulta,
            @RequestParam String codigoCidDez) {
        return consultaDiagnosticoService.listarPorConsultaECodigoCid(idConsulta, codigoCidDez);
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public List<ConsultaDiagnosticoResponse> buscarPorDescricao(@RequestParam String descricao) {
        return consultaDiagnosticoService.buscarPorDescricao(descricao);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_READ')")
    public ResponseEntity<ConsultaDiagnosticoResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(consultaDiagnosticoService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('DIAGNOSTICO_CREATE')")
    public ConsultaDiagnosticoResponse criar(@RequestBody ConsultaDiagnosticoRequest dto) {
        return consultaDiagnosticoService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_UPDATE')")
    public ResponseEntity<ConsultaDiagnosticoResponse> atualizar(@PathVariable Integer id, @RequestBody ConsultaDiagnosticoRequest dto) { 
        return ResponseEntity.ok(consultaDiagnosticoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('DIAGNOSTICO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        consultaDiagnosticoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
