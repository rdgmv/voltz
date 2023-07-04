package com.code4.voltz.dominio;

import java.time.LocalDate;

import lombok.*;

@NoArgsConstructor(force = true)
@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"nome", "dataNascimento"})
public class Pessoa {

	@Setter
	@NonNull
	private String nome;
	@Setter
	@NonNull
	private LocalDate dataNascimento;
	@Setter
	@NonNull
	private Sexo sexo;
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
