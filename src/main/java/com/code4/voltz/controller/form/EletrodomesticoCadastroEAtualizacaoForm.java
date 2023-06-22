package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Eletrodomestico;
import com.code4.voltz.dominio.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.Setter;

public class EletrodomesticoCadastroEAtualizacaoForm {


	
	
	@NotNull(message = "Campo nome não pode estar vazio.")
	@JsonProperty
	private String nome;
	@NotNull(message = "Campo modelo não pode estar vazio.")
	@JsonProperty
	private String modelo;
	@NotNull(message = "Campo potencia não pode estar vazio.")
	@JsonProperty
	private String potencia;
	

	public Eletrodomestico toEletrodomestico() {
		return new Eletrodomestico(nome, modelo, potencia); 
	}

}


