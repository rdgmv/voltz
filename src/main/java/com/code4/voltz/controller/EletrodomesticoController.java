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

import com.code4.voltz.controller.form.EletrodomesticoCadastroEAtualizacaoForm;
import com.code4.voltz.controller.form.EletrodomesticoConsultaEExclusaoForm;
import com.code4.voltz.dominio.Eletrodomestico;
import com.code4.voltz.repositorio.RepositorioEletrodomestico;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/eletrodomesticos")
public class EletrodomesticoController {
	@Autowired
	RepositorioEletrodomestico repo;

	@Autowired
	private Validator validator;

	@PostMapping
	public ResponseEntity<?> cadastrarEletrodomestico(@RequestBody EletrodomesticoCadastroEAtualizacaoForm eletrodomesticoCadastroForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoCadastroForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Eletrodomestico eletrodomestico = eletrodomesticoCadastroForm.toEletrodomestico();

			repo.salvar(eletrodomestico);

			return ResponseEntity.status(HttpStatus.CREATED).body(eletrodomestico);
		}

	}

	@GetMapping
	public ResponseEntity<?> consultaEndereco(@RequestBody EletrodomesticoConsultaEExclusaoForm eletrodomesticoConsultaForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoConsultaForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Eletrodomestico eletrodomestico = eletrodomesticoConsultaForm.toEletrodomestico();

			Optional<Eletrodomestico> opEletrodomestico = repo.buscar(eletrodomestico.getNome(), eletrodomestico.getModelo());

			if (opEletrodomestico.isEmpty()) {
				return ResponseEntity.badRequest().body("eletrodomestico não encontrada");
			} else {
				return ResponseEntity.ok(opEletrodomestico.get());
			}
		}

	}

	@GetMapping(value = { "/" })
	public ResponseEntity<Collection<Eletrodomestico>> findAll() {
		var eletrodomestico = repo.findAll();

		return ResponseEntity.ok(eletrodomestico);
	}

	@DeleteMapping
	public ResponseEntity<?> excluirEndereco(@RequestBody EletrodomesticoConsultaEExclusaoForm eletrodomesticoExclusaoForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoExclusaoForm);
		
		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Eletrodomestico eletrodomestico = eletrodomesticoExclusaoForm.toEletrodomestico();

			Optional<Eletrodomestico> opEletrodomestico = repo.buscar(eletrodomestico.getNome(), eletrodomestico.getModelo());

			if (opEletrodomestico.isEmpty()) {
				return ResponseEntity.badRequest().body("eletrodomestico não encontrado");
			} else {
				repo.excluir(eletrodomestico);
				return ResponseEntity.ok("eletrodomestico excluído com sucesso");
			}
		}

	}

	@PutMapping
	public ResponseEntity<?> atualizarEndereco(@RequestBody EletrodomesticoCadastroEAtualizacaoForm eletrodomesticoAtualizacaoForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoAtualizacaoForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Eletrodomestico eletrodomestico = eletrodomesticoAtualizacaoForm.toEletrodomestico();

			Optional<Eletrodomestico> opEletrodomestico = repo.atualizar(eletrodomestico);
			
			if (opEletrodomestico.isEmpty()) {
				return ResponseEntity.badRequest().body("Endereço não encontrado");
			} else {
				return ResponseEntity.ok(opEletrodomestico.get());
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



