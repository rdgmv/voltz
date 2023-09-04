package com.code4.voltz.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.code4.voltz.controller.form.PessoaConsultaForm;
import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.repositorio.EnderecoRepository;
import com.code4.voltz.repositorio.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.code4.voltz.controller.form.PessoaCadastroEAtualizacaoForm;
import com.code4.voltz.dominio.Pessoa;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

	@Autowired
	private Validator validator;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;
	@PostMapping
	public ResponseEntity<?> cadastrarPessoa(@RequestBody PessoaCadastroEAtualizacaoForm pessoaCadastroForm) {
		Map<Path, String> violacoesMap = validar(pessoaCadastroForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Pessoa pessoa = pessoaCadastroForm.toPessoa();

			Optional<Endereco> endereco = enderecoRepository.findById(pessoa.getEndereco().getId());

			if (endereco.isEmpty()){
				return ResponseEntity.badRequest().
						body("Endereço informado para o cadastramento da pessoa não encontrado.");
			} else {
				pessoa.setEndereco(endereco.get());
				pessoaRepository.save(pessoa);
				return ResponseEntity.status(HttpStatus.CREATED).body(pessoa);
			}

		}
	}

	@GetMapping(value = { "/id/{id}" })
	public ResponseEntity<?> consultarPessoaId(@PathVariable int id){

		Optional<Pessoa> opPessoa = pessoaRepository.findById(id);

		if (opPessoa.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Pessoa não encontrada para o ID informado.");
		} else {
			return ResponseEntity.ok(opPessoa.get());
		}

	}
	@GetMapping
	public ResponseEntity<?> consultarPessoaNomeEDataNascimento(
			@RequestBody PessoaConsultaForm pessoaConsultaForm) {
		Map<Path, String> violacoesMap = validar(pessoaConsultaForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Pessoa pessoa = pessoaConsultaForm.toPessoa();

			List<Pessoa> listPessoa =
					pessoaRepository.findByNomeAndDataNascimento(pessoa.getNome(), pessoa.getDataNascimento());

			if (listPessoa.isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_FOUND).
						body("Pessoa não encontrada para o nome e data de nascimento informados.");
			} else {
				return ResponseEntity.ok(listPessoa);
			}

		}

	}
	@GetMapping(value = { "/usuario/{id}" })
	public ResponseEntity<?> consultarPessoaUsuarioId(
			@PathVariable int id){

		List<Endereco> listEndereco = enderecoRepository.findByUsuarioId(id);
		if (listEndereco.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Pessoa(s) não encontrada(s) para o ID de usuário informado.");
		}

		List<Pessoa> listPessoasDeUmUsuario = new ArrayList<>();
		for (Endereco endereco : listEndereco){
			List<Pessoa> listPessoasDeUmEndereco =
					pessoaRepository.findByEnderecoId(endereco.getId());
			listPessoasDeUmUsuario.addAll(listPessoasDeUmEndereco);
		}

		if (listPessoasDeUmUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Pessoa(s) não encontrada(s) para o ID de usuário informado.");
		} else {
			return ResponseEntity.ok(listPessoasDeUmUsuario);
		}
	}
	@GetMapping(value = { "/endereco/{id}" })
	public ResponseEntity<?> consultarPessoaEnderecoId(
			@PathVariable int id){

		List<Pessoa> listPessoa = pessoaRepository.findByEnderecoId(id);

		if (listPessoa.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Pessoa(s) não encontrada(s) para o ID de endereço informado.");
		} else {
			return ResponseEntity.ok(listPessoa);
		}
	}
	@GetMapping(value = { "/usuarioendereco/{idUsr}/{idEnd}" })
	public ResponseEntity<?> consultarPessoaUsuarioIdEEnderecoId(
			@PathVariable int idUsr,
			@PathVariable int idEnd){

		List<Endereco> listEnderecosDeUmUsuario = enderecoRepository.findByUsuarioId(idUsr);

		List<Pessoa> listPessoasDeUmUsuarioEEndereco = new ArrayList<>();
		for (Endereco endereco : listEnderecosDeUmUsuario){
			if (endereco.getId() == idEnd) {
				listPessoasDeUmUsuarioEEndereco.
						addAll(pessoaRepository.findByEnderecoId(endereco.getId()));
			}
		}

		if (listPessoasDeUmUsuarioEEndereco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Eletrodoméstico(s) não encontrado(s) para os IDs de usuário e endereço informados.");
		} else {
			return ResponseEntity.ok(listPessoasDeUmUsuarioEEndereco);
		}
	}

	@GetMapping(value = { "/" })
	public ResponseEntity<Collection<Pessoa>> findAll() {
		var pessoas = pessoaRepository.findAll();
		return ResponseEntity.ok(pessoas);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirPessoaId(@PathVariable int id) {

		Optional<Pessoa> opPessoa = pessoaRepository.findById(id);

		if (opPessoa.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada para exclusão.");
		} else {
			pessoaRepository.deleteById(id);
			return ResponseEntity.ok("Pessoa excluída com sucesso.");
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
	        Optional<Pessoa> pessoaExistente = pessoaRepository.findById(id);

	        if (pessoaExistente.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada.");
	        } else {
	            Pessoa pessoa = pessoaAtualizacaoForm.toPessoa();

	            Pessoa pessoaAtualizada = pessoaExistente.get();
	            pessoaAtualizada.setNome(pessoa.getNome());
	            pessoaAtualizada.setDataNascimento(pessoa.getDataNascimento());
	            pessoaAtualizada.setSexo(pessoa.getSexo());
	            pessoaAtualizada.setParentescoComUsuario(pessoa.getParentescoComUsuario());

				Optional<Endereco> endereco = enderecoRepository.findById(pessoa.getEndereco().getId());

				if (endereco.isEmpty()){
					return ResponseEntity.badRequest().
							body("Endereço informado para a atualização da pessoa não encontrado.");
				}

				pessoaAtualizada.setEndereco(pessoa.getEndereco());

	            pessoaRepository.save(pessoaAtualizada);
	            return ResponseEntity.ok(pessoaAtualizada);
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
