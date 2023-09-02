package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.dominio.Usuario;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class EnderecoCadastroEAtualizacaoForm {

	@NotNull(message = "Campo rua não pode ser branco ou nulo.")
	@JsonProperty
	private String rua;
	@NotNull(message = "Campo número não pode ser branco ou nulo.")
	@JsonProperty
	private String numero;
	@NotNull(message = "Campo bairro não pode ser branco ou nulo.")
	@JsonProperty
	private String bairro;
	@NotNull(message = "Campo cidade não pode ser branco ou nulo.")
	@JsonProperty
	private String cidade;
	@NotNull(message = "Campo estado não pode ser branco ou nulo.")
	@JsonProperty
	private String estado;
	@NotNull(message = "Campo ID do usuário não pode ser branco ou nulo.")
	@JsonProperty
	private Usuario usuario;
	

	public Endereco toEndereco() {
		return new Endereco(rua, numero, bairro, cidade, estado, usuario);
	}

}


