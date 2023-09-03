package com.code4.voltz.controller.form;

import java.time.LocalDate;

import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.dominio.Pessoa;
import com.code4.voltz.dominio.Sexo;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;


public class PessoaCadastroEAtualizacaoForm {
	
	@JsonProperty
	@NotBlank(message = "Campo nome não pode ser branco ou nulo.")
	private String nome;
	@JsonProperty
	@Past
	@NotNull(message = "Campo data de nascimento não pode ser branco ou nulo.")
	private LocalDate dataNascimento;
	@JsonProperty
	@Min(value = 0, message = "Campo sexo deve ser 0 para masculino e 1 para feminino.")
	@Max(value = 1, message = "Campo sexo deve ser 0 para masculino e 1 para feminino.")
	@NotNull(message = "Campo sexo não pode ser nulo.")
	private int sexo;
	@JsonProperty
	@NotBlank(message = "Campo parentesco com o usuário não pode ser branco ou nulo.")
	private String parentescoComUsuario;

	@JsonProperty
	@NotNull(message = "Campo ID do endereço não pode ser branco ou nulo.")
	private Endereco endereco;

	public  Pessoa toPessoa() {
		return new Pessoa(nome, dataNascimento, Sexo.values()[sexo], parentescoComUsuario, endereco);
	}

}
