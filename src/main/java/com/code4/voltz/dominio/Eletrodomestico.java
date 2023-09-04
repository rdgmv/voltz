package com.code4.voltz.dominio;

import java.time.LocalDate;

import jakarta.persistence.*;
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
	@ManyToOne
	@JoinColumn(name = "endereco_id")
	@Setter
	@NonNull
	private Endereco endereco;

	private LocalDate dataEntrada = LocalDate.now();
	public Eletrodomestico(String nome, String modelo) {
		this.nome = nome;
		this.modelo = modelo;
	}

}
