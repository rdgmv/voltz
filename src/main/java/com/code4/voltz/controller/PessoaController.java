package com.code4.voltz.controller;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code4.voltz.controller.form.PessoaForm;
import com.code4.voltz.dominio.Pessoa;
import com.code4.voltz.repositorio.RepositorioPessoa;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private Validator validator ;

	@Autowired
	private RepositorioPessoa repo;

	@PostMapping
	public ResponseEntity<?> cadastrarPessoa(@RequestBody PessoaForm pform) {
		Map<Path, String> violacoesMap = validar(pform);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Pessoa pessoa = pform.toPessoa();

			repo.salvar(pessoa);

			return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
		}
	}
	
	private   <T> Map<Path, String> validar(T dto) {
		Set<ConstraintViolation<T>> violacoes = validator.validate(dto);
		Map<Path, String> violacoesMap = violacoes.stream()
				.collect(Collectors.toMap(violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()));
		return violacoesMap;
	}

}
