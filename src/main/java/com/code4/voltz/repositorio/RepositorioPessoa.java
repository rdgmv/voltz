package com.code4.voltz.repositorio;

import java.time.LocalDate;
import java.util.Collection;
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

	public Optional<Pessoa> buscar(String nome, LocalDate dataNascimento) {
		return pessoas.stream().filter(
				pessoa -> pessoa.identificadoPor(nome, dataNascimento)).findFirst();
	}
	public Collection<Pessoa> findAll() {
		return pessoas;
	}
	public void excluir(Pessoa pessoa) {
		pessoas.remove(pessoa);
	}
	
	
	public Optional<Pessoa> atualizar(Pessoa novaPessoa) {
		Optional<Pessoa> pessoaASerBuscada = this.buscar(novaPessoa.getNome(), novaPessoa.getDataNascimento());

		if(pessoaASerBuscada.isPresent()) {
			Pessoa pessoa = pessoaASerBuscada.get();
			pessoa.setSexo(novaPessoa.getSexo());
			pessoa.setParentescoComUsuario(novaPessoa.getParentescoComUsuario());

			return Optional.of(pessoa);
		}

		return Optional.empty();
	}
	
	
}
