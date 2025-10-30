package com.avicia.api.features.clinico.exame;

import java.util.List;

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

import com.avicia.api.features.clinico.exame.request.ExameRequest;
import com.avicia.api.features.clinico.exame.response.ExameResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/exames")
@RequiredArgsConstructor
public class ExameController {

    private final ExameService exameService;

    @GetMapping
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public List<ExameResponse> listarTodos() {
        return exameService.listarTodos();
    }

    @GetMapping("/ativos")
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public List<ExameResponse> listarAtivos() {
        return exameService.listarAtivos();
    }

    @GetMapping("/ativos/ordenado")
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public List<ExameResponse> listarAtivosOrdenados() {
        return exameService.listarAtivosOrdenados();
    }

    @GetMapping("/status")
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public List<ExameResponse> listarPorStatus(@RequestParam Boolean ativo) {
        return exameService.listarPorStatus(ativo);
    }

    @GetMapping("/buscar")
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public List<ExameResponse> buscarPorNome(@RequestParam String nome) {
        return exameService.buscarPorNome(nome);
    }

    @GetMapping("/tipo")
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public List<ExameResponse> listarPorTipo(@RequestParam String tipo) {
        return exameService.listarPorTipo(tipo);
    }

    @GetMapping("/tipo/ordenado")
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public List<ExameResponse> listarPorTipoOrdenado(@RequestParam String tipo) {
        return exameService.listarPorTipoOrdenado(tipo);
    }

    @GetMapping("/tipo-status")
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public List<ExameResponse> listarPorTipoEStatus(
            @RequestParam String tipo,
            @RequestParam Boolean ativo) {
        return exameService.listarPorTipoEStatus(tipo, ativo);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAME_READ')")
    public ResponseEntity<ExameResponse> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(exameService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('EXAME_CREATE')")
    public ExameResponse criar(@RequestBody ExameRequest dto) {
        return exameService.criar(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAME_UPDATE')")
    public ResponseEntity<ExameResponse> atualizar(@PathVariable Integer id, @RequestBody ExameRequest dto) { 
        return ResponseEntity.ok(exameService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/ativar")
    @PreAuthorize("hasAuthority('EXAME_UPDATE')")
    public ResponseEntity<ExameResponse> ativar(@PathVariable Integer id) {
        return ResponseEntity.ok(exameService.ativar(id));
    }

    @PatchMapping("/{id}/desativar")
    @PreAuthorize("hasAuthority('EXAME_UPDATE')")
    public ResponseEntity<ExameResponse> desativar(@PathVariable Integer id) {
        return ResponseEntity.ok(exameService.desativar(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('EXAME_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        exameService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
