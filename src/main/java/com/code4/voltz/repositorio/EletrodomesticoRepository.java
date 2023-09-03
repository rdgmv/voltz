package com.code4.voltz.repositorio;

import com.code4.voltz.dominio.Eletrodomestico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EletrodomesticoRepository extends JpaRepository<Eletrodomestico, Integer> {
	List<Eletrodomestico> findByNomeAndModelo(String nome, String modelo);
	List<Eletrodomestico> findByEnderecoId(int id);

}
