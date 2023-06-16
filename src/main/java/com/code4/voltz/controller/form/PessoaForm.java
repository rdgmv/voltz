package com.code4.voltz.controller.form;

import java.time.LocalDate;

import com.code4.voltz.dominio.Pessoa;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;


public class PessoaForm {
	
	@JsonProperty
	@NotBlank(message = "Não deve ser Branco ou Nulo")
	private String nome;
	@JsonProperty

	@Past
	@NotNull(message = "Não deve ser Branco ou Nulo")
	private LocalDate dataNascimento;
	@JsonProperty
	@NotBlank(message = "Não deve ser Branco ou Nulo")
	private String sexo;
	@JsonProperty
	@NotBlank(message = "Não deve ser Branco ou Nulo")
	private String parentescoComUsuario;
	
	public  Pessoa toPessoa() {
		
		return new Pessoa(nome, dataNascimento, sexo, parentescoComUsuario);
	}

}
