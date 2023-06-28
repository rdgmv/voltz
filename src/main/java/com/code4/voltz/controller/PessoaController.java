package com.code4.voltz.controller;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.code4.voltz.controller.form.PessoaConsultaEExclusaoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.code4.voltz.controller.form.PessoaCadastroEAtualizacaoForm;
import com.code4.voltz.dominio.Pessoa;
import com.code4.voltz.repositorio.RepositorioPessoa;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private Validator validator;

	@Autowired
	private RepositorioPessoa repo;

	@PostMapping
	public ResponseEntity<?> cadastrarPessoa(@RequestBody PessoaCadastroEAtualizacaoForm pessoaCadastroForm) {
		Map<Path, String> violacoesMap = validar(pessoaCadastroForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Pessoa pessoa = pessoaCadastroForm.toPessoa();

			repo.salvar(pessoa);

			return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
		}
	}

	@GetMapping
	public ResponseEntity<?> consultarPessoa(@RequestBody PessoaConsultaEExclusaoForm pessoaConsultaForm) {
		Map<Path, String> violacoesMap = validar(pessoaConsultaForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Pessoa pessoa = pessoaConsultaForm.toPessoa();

			Optional<Pessoa> opPessoa = repo.buscar(pessoa.getNome(), pessoa.getDataNascimento());

			if (opPessoa.isEmpty()) {
				return ResponseEntity.badRequest().body("Pessoa não encontrada");
			} else {
				return ResponseEntity.ok(opPessoa.get());
			}
		}

	}

	@GetMapping(value = { "/" })
	public ResponseEntity<Collection<Pessoa>> findAll() {
		var pessoas = repo.findAll();
		return ResponseEntity.ok(pessoas);
	}

	@DeleteMapping
	public ResponseEntity<?> excluirPessoa(@RequestBody PessoaConsultaEExclusaoForm pessoaExclusaoForm) {
		Map<Path, String> violacoesMap = validar(pessoaExclusaoForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Pessoa pessoa = pessoaExclusaoForm.toPessoa();

			Optional<Pessoa> opPessoa = repo.buscar(pessoa.getNome(), pessoa.getDataNascimento());

			if (opPessoa.isEmpty()) {
				return ResponseEntity.badRequest().body("Pessoa não encontrada.");
			} else {
				repo.excluir(pessoa);
				return ResponseEntity.ok("Pessoa excluída com sucesso.");
			}
		}

	}

	@PutMapping
	public ResponseEntity<?> atualizarPessoa(@RequestBody PessoaCadastroEAtualizacaoForm pessoaAtualizacaoForm) {
		Map<Path, String> violacoesMap = validar(pessoaAtualizacaoForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Pessoa pessoa = pessoaAtualizacaoForm.toPessoa();

			Optional<Pessoa> opPessoa = repo.atualizar(pessoa);
			
			if (opPessoa.isEmpty()) {
				return ResponseEntity.badRequest().body("Pessoa não encontrada.");
			} else {
				return ResponseEntity.ok(opPessoa.get());
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
