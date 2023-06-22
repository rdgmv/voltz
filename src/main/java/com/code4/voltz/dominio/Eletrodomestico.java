package com.code4.voltz.dominio;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of = { "nome", "modelo" })
@RequiredArgsConstructor
public class Eletrodomestico {

	@Setter
	@NonNull
	private String nome;
	@Setter
	@NonNull
	private String modelo;
	@Setter
	@NonNull
	private String potencia;

	private LocalDate dataEntrada = LocalDate.now();

	public boolean identificadoPor(String nome, String modelo) {
		return this.nome.equals(nome) && this.modelo.equals(modelo);

	}

	public Eletrodomestico(@NonNull String nome, @NonNull String modelo) {
		super();
		this.nome = nome;
		this.modelo = modelo;
	}

}
