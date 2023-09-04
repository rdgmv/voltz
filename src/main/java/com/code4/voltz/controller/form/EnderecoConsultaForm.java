package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class EnderecoConsultaForm {
	
	@JsonProperty
	@NotBlank(message = "Campo rua não pode ser branco ou nulo.")
	private String rua;

	@NotBlank(message = "Campo número não pode ser branco ou nulo.")
	@JsonProperty
	private String numero;

	public Endereco toEndereco() {
		return new Endereco(rua, numero);
	}

}
