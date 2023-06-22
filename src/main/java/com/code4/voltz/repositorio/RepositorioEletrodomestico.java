package com.code4.voltz.repositorio;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.code4.voltz.dominio.Eletrodomestico;

@Repository
public class RepositorioEletrodomestico {
	
	
	private Set<Eletrodomestico> eletrodomesticos;

	public RepositorioEletrodomestico() {
		eletrodomesticos = new HashSet<>();
	}

	public void salvar(Eletrodomestico eletrodomestico) {
		eletrodomesticos.add(eletrodomestico);
	}

	public Optional<Eletrodomestico> buscar(String nome, String modelo) {
		return eletrodomesticos.stream().filter(eletrodomestico -> eletrodomestico.identificadoPor(nome, modelo)).findFirst();
	}

	public Collection<Eletrodomestico> findAll() {
		return eletrodomesticos;
	}

	public void excluir(Eletrodomestico eletrodomestico) {
		eletrodomesticos.remove(eletrodomestico);
	}

	public Optional<Eletrodomestico> atualizar(Eletrodomestico novoEletrodomestico) {
		Optional<Eletrodomestico> eletrodomesticoASerBuscado = this.buscar(novoEletrodomestico.getNome(), novoEletrodomestico.getModelo());

		if (eletrodomesticoASerBuscado.isPresent()) {
			Eletrodomestico eletrodomestico = eletrodomesticoASerBuscado.get();
			eletrodomestico.setPotencia(novoEletrodomestico.getPotencia());


			return Optional.of(eletrodomestico);
		}

		return Optional.empty();
	}
}
