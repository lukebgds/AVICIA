package com.avicia.api.features.usuario.request;

public record AlterarSenhaRequest
(
    String senhaAtual,
    String senhaNova
) {

}
