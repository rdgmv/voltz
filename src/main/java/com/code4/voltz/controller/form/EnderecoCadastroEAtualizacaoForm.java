package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;

public class EnderecoCadastroEAtualizacaoForm {

	@NotNull(message = "Campo rua não pode estar vazio.")
	@JsonProperty
	private String rua;
	@NotNull(message = "Campo numero não pode estar vazio.")
	@JsonProperty
	private String numero;
	@NotNull(message = "Campo bairro não pode estar vazio.")
	@JsonProperty
	private String bairro;
	@NotNull(message = "Campo cidade não pode estar vazio.")
	@JsonProperty
	private String cidade;
	@NotNull(message = "Campo estado não pode estar vazio.")
	@JsonProperty
	private String estado;
	

	public Endereco toEndereco() {
		return new Endereco(rua, numero, bairro, cidade, estado); 
	}

}


