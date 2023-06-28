package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Eletrodomestico;
import com.code4.voltz.dominio.Pessoa;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class EletrodomesticoConsultaEExclusaoForm {
    @JsonProperty
    @NotBlank(message = "Campo nome não pode ser branco ou nulo.")
    private String nome;
    @JsonProperty
    @NotNull(message = "Campo modelo não pode ser branco ou nulo.")
    private String modelo;

    public Eletrodomestico toEletrodomestico() {
        return new Eletrodomestico(nome, modelo);
    }
}
