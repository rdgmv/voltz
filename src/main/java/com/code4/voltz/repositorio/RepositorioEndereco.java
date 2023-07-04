package com.code4.voltz.repositorio;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.code4.voltz.dominio.Eletrodomestico;
import org.springframework.stereotype.Repository;

import com.code4.voltz.dominio.Endereco;

@Repository
public class RepositorioEndereco {

	private Set<Endereco> enderecos;

	public RepositorioEndereco() {
		enderecos = new HashSet<>();
	}

	public Optional<Endereco> salvar(Endereco endereco) {
		Optional<Endereco> enderecoASerCadastrado = this.buscar(endereco.getRua(), endereco.getNumero());

		if(enderecoASerCadastrado.isEmpty()) {
			enderecos.add(endereco);
			return Optional.of(endereco);
		}
		return Optional.empty();
	}
	public Optional<Endereco> buscar(String rua, String numero) {
		return enderecos.stream().filter(endereco -> endereco.identificadoPor(rua, numero)).findFirst();
	}

	public Collection<Endereco> findAll() {
		return enderecos;
	}

	public void excluir(Endereco endereco) {
		enderecos.remove(endereco);
	}

	public Optional<Endereco> atualizar(Endereco novoEndereco) {
		Optional<Endereco> enderecoASerBuscado = this.buscar(novoEndereco.getRua(), novoEndereco.getNumero());

		if (enderecoASerBuscado.isPresent()) {
			Endereco endereco = enderecoASerBuscado.get();
			endereco.setBairro(novoEndereco.getBairro());
			endereco.setCidade(novoEndereco.getCidade());

			return Optional.of(endereco);
		}

		return Optional.empty();
	}
}