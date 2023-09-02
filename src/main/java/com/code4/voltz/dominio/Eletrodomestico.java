package com.code4.voltz.dominio;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor(force = true)
@Getter
@EqualsAndHashCode(of = { "nome", "modelo" })
@RequiredArgsConstructor
@Entity
public class Eletrodomestico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	private int id;
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
