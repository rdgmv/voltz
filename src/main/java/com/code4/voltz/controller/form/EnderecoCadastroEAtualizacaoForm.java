package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.dominio.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class EnderecoCadastroEAtualizacaoForm {

	@NotBlank(message = "Campo rua não pode ser branco ou nulo.")
	@JsonProperty
	private String rua;
	@NotBlank(message = "Campo número não pode ser branco ou nulo.")
	@JsonProperty
	private String numero;
	@NotBlank(message = "Campo bairro não pode ser branco ou nulo.")
	@JsonProperty
	private String bairro;
	@NotBlank(message = "Campo cidade não pode ser branco ou nulo.")
	@JsonProperty
	private String cidade;
	@NotBlank(message = "Campo estado não pode ser branco ou nulo.")
	@JsonProperty
	private String estado;
	@NotNull(message = "Campo ID do usuário não pode ser branco ou nulo.")
	@JsonProperty
	private Usuario usuario;
	

	public Endereco toEndereco() {
		return new Endereco(rua, numero, bairro, cidade, estado, usuario);
	}

}


