package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.dominio.Pessoa;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public class EnderecoConsultaEExclusaoForm {
	
	@JsonProperty
	@NotNull(message = "Campo rua não pode estar vazio.")
	private String rua;

	@NotNull(message = "Campo numero não pode estar vazio.")
	@JsonProperty
	private String numero;

	public Endereco toEndereco() {
		return new Endereco(rua, numero);
	}

}
