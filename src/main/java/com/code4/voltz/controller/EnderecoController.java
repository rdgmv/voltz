package com.code4.voltz.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.code4.voltz.dominio.Eletrodomestico;
import com.code4.voltz.dominio.Pessoa;
import com.code4.voltz.dominio.Usuario;
import com.code4.voltz.repositorio.EletrodomesticoRepository;
import com.code4.voltz.repositorio.EnderecoRepository;
import com.code4.voltz.repositorio.PessoaRepository;
import com.code4.voltz.repositorio.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.code4.voltz.controller.form.EnderecoCadastroEAtualizacaoForm;
import com.code4.voltz.controller.form.EnderecoConsultaForm;
import com.code4.voltz.dominio.Endereco;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private EletrodomesticoRepository eletrodomesticoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private Validator validator;

	@PostMapping
	public ResponseEntity<?> cadastrarEndereco(@RequestBody EnderecoCadastroEAtualizacaoForm enderecoCadastroForm) {
		Map<Path, String> violacoesMap = validar(enderecoCadastroForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Endereco endereco = enderecoCadastroForm.toEndereco();

			Optional<Usuario> opUsuario = usuarioRepository.findById(endereco.getUsuario().getId());

			if (opUsuario.isEmpty()){
				return ResponseEntity.badRequest().
						body("Usuário informado para o cadastramento do endereço não encontrado.");
			} else {
				endereco.setUsuario(opUsuario.get());
				enderecoRepository.save(endereco);
				return ResponseEntity.status(HttpStatus.CREATED).body(endereco);
			}

		}

	}

	@GetMapping(value = { "/id/{id}" })
	public ResponseEntity<?> consultarEnderecoId(@PathVariable int id){

		Optional<Endereco> opEndereco = enderecoRepository.findById(id);

		if (opEndereco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Endereço não encontrado para o ID informado.");
		} else {
			return ResponseEntity.ok(opEndereco.get());
		}

	}
	@GetMapping
	public ResponseEntity<?> consultarEnderecoRuaENumero
			(@RequestBody EnderecoConsultaForm enderecoConsultaForm) {
		Map<Path, String> violacoesMap = validar(enderecoConsultaForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Endereco endereco = enderecoConsultaForm.toEndereco();

			List<Endereco> listEndereco = enderecoRepository.
					findByRuaAndNumero(endereco.getRua(), endereco.getNumero());

			if (listEndereco.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).
						body("Endereço(s) não encontrado(s) para a rua e número informados.");
			} else {
				return ResponseEntity.ok(listEndereco);
			}
		}
	}
	@GetMapping(value = { "/usuario/{id}" })
	public ResponseEntity<?> consultarEnderecoUsuarioId(
			@PathVariable int id){

		List<Endereco> listEndereco = enderecoRepository.findByUsuarioId(id);

		if (listEndereco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Endereço(s) não encontrado(s) para o ID de usuário informado.");
		} else {
			return ResponseEntity.ok(listEndereco);
		}
	}
	@GetMapping(value = { "/" })
	public ResponseEntity<Collection<Endereco>> findAll() {
		var enderecos = enderecoRepository.findAll();
		return ResponseEntity.ok(enderecos);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirEnderecoId(@PathVariable int id) {

		Optional<Endereco> opEndereco = enderecoRepository.findById(id);

		if (opEndereco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado para exclusão.");
		} else {

			List<Pessoa> listPessoa = pessoaRepository.findByEnderecoId(id);
			List<Eletrodomestico> listEletrodomestico = eletrodomesticoRepository.findByEnderecoId(id);

			if (listPessoa.isEmpty() && listEletrodomestico.isEmpty()){
				enderecoRepository.deleteById(id);
				return ResponseEntity.ok("Endereço excluído com sucesso.");
			} else {
				return ResponseEntity.badRequest().body("Exclusão não permitida. Endereço possui pessoa(s) " +
						"e/ou eletrodoméstico(s) vinculado(s). " +
						"Para exclui-lo, desfaça primeiramente o(s) vínculo(s).");
			}

		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarEndereco(
			@PathVariable int id,
			@RequestBody EnderecoCadastroEAtualizacaoForm enderecoAtualizacaoForm) {

		Map<Path, String> violacoesMap = validar(enderecoAtualizacaoForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Optional<Endereco> enderecoExistente = enderecoRepository.findById(id);

			if (enderecoExistente.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Endereço não encontrado para atualização.");
			}

			Endereco endereco = enderecoAtualizacaoForm.toEndereco();

			Endereco enderecoAtualizado = enderecoExistente.get();

			enderecoAtualizado.setRua(endereco.getRua());
			enderecoAtualizado.setNumero(endereco.getNumero());
			enderecoAtualizado.setBairro(endereco.getBairro());
			enderecoAtualizado.setCidade(endereco.getCidade());
			enderecoAtualizado.setEstado(endereco.getEstado());

			Optional<Usuario> opUsuario = usuarioRepository.findById(endereco.getUsuario().getId());

			if (opUsuario.isEmpty()){
				return ResponseEntity.badRequest().
						body("Usuário informado para a atualização do endereço não encontrado.");
			}

			enderecoAtualizado.setUsuario(opUsuario.get());

			enderecoRepository.save(enderecoAtualizado);

			return ResponseEntity.ok(enderecoAtualizado);
		}
	}

	private <T> Map<Path, String> validar(T dto) {
		Set<ConstraintViolation<T>> violacoes = validator.validate(dto);
		Map<Path, String> violacoesMap = violacoes.stream()
				.collect(Collectors.toMap(violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()));
		return violacoesMap;
	}
}
