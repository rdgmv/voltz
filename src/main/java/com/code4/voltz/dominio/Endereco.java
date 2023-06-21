package com.code4.voltz.dominio;

import java.time.LocalDate;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@EqualsAndHashCode(of= {"rua","numero"})
@RequiredArgsConstructor
public class Endereco {

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
	
	private LocalDate dataEntrada = LocalDate.now();
	
	public boolean identificadoPor(String rua, String  numero) {
		return this.rua.equals(rua) &&
				this.numero.equals(numero) ;
		
	}

	public Endereco(@NonNull String rua, @NonNull String numero) {
		super();
		this.rua = rua;
		this.numero = numero;
	}
	
	
	
	
	
	
}
