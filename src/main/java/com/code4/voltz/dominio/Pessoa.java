package com.code4.voltz.dominio;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(force = true)
@Getter
@RequiredArgsConstructor
public class Pessoa {

	@Setter
	@NonNull
	private String nome;
	@Setter
	@NonNull
	private LocalDate dataNascimento;
	@Setter
	@NonNull
	private String sexo;
	@Setter
	@NonNull
	private String parentescoComUsuario;

	private LocalDate dataEntrada = LocalDate.now();

	public Pessoa(String nome, LocalDate dataNascimento) {
		this.nome = nome;
		this.dataNascimento = dataNascimento;
	}


	public boolean identificadoPor(String nome, LocalDate dataNascimento) {
		return this.nome.equals(nome) &&
				this.dataNascimento.equals(dataNascimento) ;
		
	}

}
