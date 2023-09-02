package com.code4.voltz.repositorio;

import com.code4.voltz.dominio.Eletrodomestico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EletrodomesticoRepository extends JpaRepository<Eletrodomestico, Integer> {
	
	Eletrodomestico findByNomeAndModelo(String nome, String modelo);

	
}
