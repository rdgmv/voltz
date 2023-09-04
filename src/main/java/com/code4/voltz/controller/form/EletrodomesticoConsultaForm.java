package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Eletrodomestico;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class EletrodomesticoConsultaForm {
    @JsonProperty
    @NotBlank(message = "Campo nome não pode ser branco ou nulo.")
    private String nome;
    @JsonProperty
    @NotBlank(message = "Campo modelo não pode ser branco ou nulo.")
    private String modelo;

    public Eletrodomestico toEletrodomestico() {
        return new Eletrodomestico(nome, modelo);
    }
}
