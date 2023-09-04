package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Eletrodomestico;
import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.dominio.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.Setter;

public class EletrodomesticoCadastroEAtualizacaoForm {

	@NotBlank(message = "Campo nome não pode ser branco ou nulo.")
	@JsonProperty
	private String nome;
	@NotBlank(message = "Campo modelo não pode ser branco ou nulo.")
	@JsonProperty
	private String modelo;
	@NotNull(message = "Campo potência não pode ser nulo.")
	@Min(value = 0, message = "Campo potência deve maior que zero.")
	@Max(value = 99999, message = "Campo potência não deve ser maior que 99.999.")
	@JsonProperty
	private int potencia;
	@NotNull(message = "Campo ID do endereço não pode ser nulo.")
	@JsonProperty
	private Endereco endereco;

	public Eletrodomestico toEletrodomestico() {
		return new Eletrodomestico(nome, modelo, potencia, endereco);
	}

}


