package com.avicia.api.features.usuario;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avicia.api.features.usuario.request.AlterarSenhaRequest;
import com.avicia.api.features.usuario.request.UsuarioRequest;
import com.avicia.api.features.usuario.response.CriarUsuarioResponse;
import com.avicia.api.features.usuario.response.UsuarioResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    
    @PostMapping("/cadastro")// localhost:9081/api/usuarios
    public ResponseEntity<CriarUsuarioResponse> criar(@RequestBody UsuarioRequest dto) {
        return ResponseEntity.ok(usuarioService.criar(dto));
    }

    @GetMapping // localhost:9081/api/usuarios
    @PreAuthorize("hasAuthority('USUARIO_READ')")
    public ResponseEntity<List<UsuarioResponse>> listarTodos() {
        return ResponseEntity.ok(usuarioService.listarTodos());
    }
    
    @GetMapping("/{cpf}")
    @PreAuthorize("hasAuthority('USUARIO_READ')")
    public ResponseEntity<UsuarioResponse> buscarPorCpf(@PathVariable String cpf) {
        return ResponseEntity.ok(usuarioService.buscaPorCpf(cpf));
    }

    @PutMapping("/{cpf}") // localhost:9081/api/usuarios/{cpf}
    @PreAuthorize("hasAuthority('USUARIO_UPDATE')")
    public ResponseEntity<UsuarioResponse> atualizar(@PathVariable String cpf, @RequestBody UsuarioRequest dto) {
        return ResponseEntity.ok(usuarioService.atualizar(cpf, dto));
    }

    // localhost:9081/api/usuarios/{cpf}/senha
    @PutMapping("/{cpf}/alterar-senha")
    @PreAuthorize("hasAuthority('USUARIO_UPDATE')")
    public ResponseEntity<UsuarioResponse> atualizarSenha(@PathVariable String cpf, @RequestBody AlterarSenhaRequest request) {
        UsuarioResponse usuario = usuarioService.atualizarSenha(cpf, request.senhaAtual(), request.senhaNova());
        return ResponseEntity.ok(usuario);
    }

    @DeleteMapping("/{idUsuario}") // localhost:9081/api/usuarios/{cpf}
    @PreAuthorize("hasAuthority('USUARIO_DELETE')")
    public ResponseEntity<Void> deletar(@PathVariable Integer idUsuario) {
        usuarioService.deletar(idUsuario);
        return ResponseEntity.noContent().build();
    }

}
