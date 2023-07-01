package com.code4.voltz.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code4.voltz.controller.form.EnderecoCadastroEAtualizacaoForm;
import com.code4.voltz.controller.form.EnderecoConsultaEExclusaoForm;
import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.repositorio.RepositorioEndereco;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	RepositorioEndereco repo;

	@Autowired
	private Validator validator;

	@PostMapping
	public ResponseEntity<?> cadastrarEndereco(@RequestBody EnderecoCadastroEAtualizacaoForm enderecoCadastroForm) {
		Map<Path, String> violacoesMap = validar(enderecoCadastroForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Endereco endereco = enderecoCadastroForm.toEndereco();

			repo.salvar(endereco);

			return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
		}

	}

	@GetMapping
	public ResponseEntity<?> consultaEndereco(@RequestBody EnderecoConsultaEExclusaoForm enderecoConsultaForm) {
		Map<Path, String> violacoesMap = validar(enderecoConsultaForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Endereco endereco = enderecoConsultaForm.toEndereco();

			Optional<Endereco> opEndereco = repo.buscar(endereco.getRua(), endereco.getNumero());

			if (opEndereco.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado.");
			} else {
				return ResponseEntity.ok(opEndereco.get());
			}
		}

	}

	@GetMapping(value = { "/" })
	public ResponseEntity<Collection<Endereco>> findAll() {
		var enderecos = repo.findAll();

		return ResponseEntity.ok(enderecos);
	}

	@DeleteMapping
	public ResponseEntity<?> excluirEndereco(@RequestBody EnderecoConsultaEExclusaoForm enderecoExclusaoForm) {
		Map<Path, String> violacoesMap = validar(enderecoExclusaoForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Endereco endereco = enderecoExclusaoForm.toEndereco();

			Optional<Endereco> opEndereco = repo.buscar(endereco.getRua(), endereco.getNumero());

			if (opEndereco.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado.");
			} else {
				repo.excluir(endereco);
				return ResponseEntity.ok("Endereço excluído com sucesso.");
			}
		}

	}

	@PutMapping
	public ResponseEntity<?> atualizarEndereco(@RequestBody EnderecoCadastroEAtualizacaoForm enderecoAtualizacaoForm) {
		Map<Path, String> violacoesMap = validar(enderecoAtualizacaoForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Endereco endereco = enderecoAtualizacaoForm.toEndereco();

			Optional<Endereco> opEndereco = repo.atualizar(endereco);
			
			if (opEndereco.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado.");
			} else {
				return ResponseEntity.ok(opEndereco.get());
			}
		}
	}

	private <T> Map<Path, String> validar(T dto) {
		Set<ConstraintViolation<T>> violacoes = validator.validate(dto);
		Map<Path, String> violacoesMap = violacoes.stream()
				.collect(Collectors.toMap(violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()));
		return violacoesMap;
	}
}
