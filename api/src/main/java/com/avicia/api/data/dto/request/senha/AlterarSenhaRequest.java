package com.avicia.api.data.dto.request.senha;

public record AlterarSenhaRequest
(
    String senhaAtual,
    String senhaNova
) {

}
