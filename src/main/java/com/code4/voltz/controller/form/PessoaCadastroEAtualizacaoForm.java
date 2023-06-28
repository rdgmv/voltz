package com.code4.voltz.controller.form;

import java.time.LocalDate;

import com.code4.voltz.dominio.Pessoa;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;


public class PessoaCadastroEAtualizacaoForm {
	
	@JsonProperty
	@NotBlank(message = "Campo nome não pode ser branco ou nulo.")
	private String nome;
	@JsonProperty
	@Past
	@NotNull(message = "Campo data de nascimento não pode ser branco ou nulo.")
	private LocalDate dataNascimento;
	@JsonProperty
	@NotBlank(message = "Campo sexo não pode ser branco ou nulo.")
	private String sexo;
	@JsonProperty
	@NotBlank(message = "Campo parentesco com o usuário não pode ser branco ou nulo.")
	private String parentescoComUsuario;
	
	public  Pessoa toPessoa() {
		
		return new Pessoa(nome, dataNascimento, sexo, parentescoComUsuario);
	}

}
