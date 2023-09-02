package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class UsuarioForm {
    @JsonProperty
    @NotBlank(message = "Campo nome não pode ser branco ou nulo.")
    private String nome;

    public Usuario toUsuario() {
        return new Usuario(nome);
    }
}
