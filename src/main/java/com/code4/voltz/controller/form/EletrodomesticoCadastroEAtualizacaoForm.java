package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Eletrodomestico;
import com.code4.voltz.dominio.Endereco;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.Setter;

public class EletrodomesticoCadastroEAtualizacaoForm {


	
	
	@NotNull(message = "Campo nome não pode ser branco ou nulo.")
	@JsonProperty
	private String nome;
	@NotNull(message = "Campo modelo não pode ser branco ou nulo.")
	@JsonProperty
	private String modelo;
	@NotNull(message = "Campo potência não pode ser branco ou nulo.")
	@JsonProperty
	private String potencia;
	

	public Eletrodomestico toEletrodomestico() {
		return new Eletrodomestico(nome, modelo, potencia); 
	}

}


