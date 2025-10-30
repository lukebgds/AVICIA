package com.avicia.api.features.paciente;

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
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.features.associacao.paciente.VerificarAcessoPaciente;
import com.avicia.api.features.paciente.request.PacienteRequest;
import com.avicia.api.features.paciente.response.PacienteResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteService pacienteService;
    private final VerificarAcessoPaciente verificarAcessoPaciente;

    @GetMapping
    @PreAuthorize("hasAuthority('PACIENTE_READ')")
    public List<PacienteResponse> listarTodos() {
        //verificarAcessoPaciente.verificarAcesso(); // TALVEZ ISSO PRECISE DE UM MÉTODO DIFERENTE
        return pacienteService.listarTodos();
    }

    @GetMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    @PreAuthorize("hasAuthority('PACIENTE_READ')")
    public ResponseEntity<PacienteResponse> buscarPorCpf(@PathVariable String cpf) {
        verificarAcessoPaciente.verificarAcesso(cpf);
        return ResponseEntity.ok(pacienteService.buscarPorCpf(cpf));
    }

    @PostMapping("/cadastro") // localhost:9081/api/pacientes/cadastro
    public PacienteResponse criar(@RequestBody PacienteRequest dto) {
        return pacienteService.criar(dto);
    }

    @PutMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    @PreAuthorize("hasAuthority('PACIENTE_UPDATE')")
    public ResponseEntity<PacienteResponse> atualizar(@PathVariable String cpf, @RequestBody PacienteRequest dto) { 
        verificarAcessoPaciente.verificarAcesso(cpf);
        return ResponseEntity.ok(pacienteService.atualizar(cpf, dto));
    }

    @DeleteMapping("/{cpf}") // localhost:9081/api/pacientes/{cpf}
    @PreAuthorize("hasAuthority('PACIENTE_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable String cpf) {
        verificarAcessoPaciente.verificarAcesso(cpf);
        pacienteService.deletar(cpf);
        return ResponseEntity.noContent().build();
    }
}
