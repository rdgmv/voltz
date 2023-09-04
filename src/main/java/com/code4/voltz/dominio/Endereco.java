package com.code4.voltz.dominio;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor(force = true)
@Getter
@EqualsAndHashCode(of = { "rua", "numero" })
@RequiredArgsConstructor
@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Setter
	@NonNull
	private String rua;
	@Setter
	@NonNull
	private String numero;
	@Setter
	@NonNull
	private String bairro;
	@Setter
	@NonNull
	private String cidade;
	@Setter
	@NonNull
	private String estado;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	@Setter
	@NonNull
	private Usuario usuario;

	private LocalDate dataEntrada = LocalDate.now();

	public Endereco(String rua, String numero) {
		this.rua = rua;
		this.numero = numero;
	}

}
