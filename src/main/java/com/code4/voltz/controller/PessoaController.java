package com.code4.voltz.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.code4.voltz.controller.form.PessoaConsultaEExclusaoForm;
import com.code4.voltz.repositorio.PessoaRepository;
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

	@Autowired
	private PessoaRepository pessoaRepository;
	@PostMapping
	public ResponseEntity<?> cadastrarPessoa(@RequestBody PessoaCadastroEAtualizacaoForm pessoaCadastroForm) {
		Map<Path, String> violacoesMap = validar(pessoaCadastroForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Pessoa pessoa = pessoaCadastroForm.toPessoa();

			pessoaRepository.save(pessoa);

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

			List<Pessoa> listPessoa =
					pessoaRepository.findByNomeAndDataNascimento(pessoa.getNome(), pessoa.getDataNascimento());

			if (listPessoa.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
			} else {
				return ResponseEntity.ok(listPessoa);
			}

		}

	}

	@GetMapping(value = { "/" })
	public ResponseEntity<Collection<Pessoa>> findAll() {
		var pessoas = pessoaRepository.findAll();
		return ResponseEntity.ok(pessoas);
	}

	@DeleteMapping
	public ResponseEntity<?> excluirPessoa(@RequestBody PessoaConsultaEExclusaoForm pessoaExclusaoForm) {
		Map<Path, String> violacoesMap = validar(pessoaExclusaoForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Pessoa pessoa = pessoaExclusaoForm.toPessoa();

			List<Pessoa> listPessoa =
					pessoaRepository.findByNomeAndDataNascimento(pessoa.getNome(), pessoa.getDataNascimento());

			if (listPessoa.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
			} else {
				return ResponseEntity.ok("Pessoa excluída com sucesso.");
			}
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarPessoa(
			@PathVariable int id,
			@RequestBody PessoaCadastroEAtualizacaoForm pessoaAtualizacaoForm) {
	    Map<Path, String> violacoesMap = validar(pessoaAtualizacaoForm);

	    if (!violacoesMap.isEmpty()) {
	        return ResponseEntity.badRequest().body(violacoesMap);
	    } else {
	        // Verificamos se a pessoa com o ID fornecido existe no banco de dados
	        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);

	        if (pessoaExistente.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
	        } else {
	            Pessoa pessoa = pessoaAtualizacaoForm.toPessoa();

	            // Atualizamos as propriedades da pessoa existente com as do objeto recebido
	            Pessoa pessoaAtualizada = pessoaExistente.get();
	            pessoaAtualizada.setNome(pessoa.getNome());
	            pessoaAtualizada.setDataNascimento(pessoa.getDataNascimento());
	            pessoaAtualizada.setSexo(pessoa.getSexo());
	            pessoaAtualizada.setParentescoComUsuario(pessoa.getParentescoComUsuario());
	            // Continue com a atualização de outros campos, se necessário

	            // Salve as alterações no banco de dados
	            Pessoa pessoaSalva = pessoaRepository.save(pessoaAtualizada);
	            return ResponseEntity.ok(pessoaSalva);
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
