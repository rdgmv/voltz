package com.code4.voltz.repositorio;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.code4.voltz.dominio.Pessoa;

@Repository
public class RepositorioPessoa {

	private Set<Pessoa> pessoas;

	public RepositorioPessoa() {
		pessoas = new HashSet<>();
	}

	public void salvar(Pessoa pessoa) {
		pessoas.add(pessoa);
	}

	public Optional<Pessoa> buscar(String nome, String dataNascimento) {
		return pessoas.stream().filter(pessoa -> pessoa.identificaPor(nome, dataNascimento)).findFirst();
	}
	
	
	public void deletar(Pessoa pessoa) {
		pessoas.remove(pessoa);
	}
	
	
	public void atualizaPessoa(Pessoa novaPessoa) {
		for (Pessoa pessoa : pessoas ) {
			if (pessoa.getNome().equals(novaPessoa)) {
				pessoa.setDataNascimento(novaPessoa.getDataNascimento());
			}
		}
	}
	
	
}
