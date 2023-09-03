package com.code4.voltz.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.repositorio.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code4.voltz.controller.form.EletrodomesticoCadastroEAtualizacaoForm;
import com.code4.voltz.controller.form.EletrodomesticoConsultaEExclusaoForm;
import com.code4.voltz.dominio.Eletrodomestico;
import com.code4.voltz.repositorio.EletrodomesticoRepository;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/eletrodomesticos")
public class EletrodomesticoController {
	
	@Autowired
	EletrodomesticoRepository eletrodomesticoRepository; 

	@Autowired
	EnderecoRepository enderecoRepository;

	@Autowired
	private Validator validator;

	@PostMapping
	public ResponseEntity<?> cadastrarEletrodomestico(
			@RequestBody EletrodomesticoCadastroEAtualizacaoForm eletrodomesticoCadastroForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoCadastroForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Eletrodomestico eletrodomestico = eletrodomesticoCadastroForm.toEletrodomestico();

			eletrodomesticoRepository.save(eletrodomestico);

			return ResponseEntity.status(HttpStatus.CREATED).body(eletrodomestico);
		}

	}

	@GetMapping(value = { "/id/{id}" })
	public ResponseEntity<?> consultarEletrodomesticoId(@PathVariable int id){

		Optional<Eletrodomestico> opEletrodomestico = eletrodomesticoRepository.findById(id);

		if (opEletrodomestico.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Eletrodoméstico não encontrado para o ID informado.");
		} else {
			return ResponseEntity.ok(opEletrodomestico.get());
		}

	}
	@GetMapping
	public ResponseEntity<?> consultarEletrodomesticoNomeEModelo(
			@RequestBody EletrodomesticoConsultaEExclusaoForm eletrodomesticoConsultaForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoConsultaForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Eletrodomestico eletrodomestico = eletrodomesticoConsultaForm.toEletrodomestico();

			List<Eletrodomestico> listEletrodomestico = eletrodomesticoRepository.
					findByNomeAndModelo(eletrodomestico.getNome(), eletrodomestico.getModelo());
			
			if (listEletrodomestico.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).
						body("Eletrodoméstico não encontrado para o nome e modelo informados.");
			} else {
				return ResponseEntity.ok(listEletrodomestico);
			}
		}

	}

	@GetMapping(value = { "/usuario/{id}" })
	public ResponseEntity<?> consultarEletrodomesticoUsuarioId(
			@PathVariable int id){

		List<Endereco> listEndereco = enderecoRepository.findByUsuarioId(id);
		if (listEndereco.isEmpty()){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Eletrodoméstico(s) não encontrado(s) para o ID de usuário informado.");
		}

		List<Eletrodomestico> listEletrodomesticosDeUmUsuario = new ArrayList<>();
		for (Endereco endereco : listEndereco){
			List<Eletrodomestico> listEletrodomesticoDeUmEndereco =
					eletrodomesticoRepository.findByEnderecoId(endereco.getId());
			listEletrodomesticosDeUmUsuario.addAll(listEletrodomesticoDeUmEndereco);
		}

		if (listEletrodomesticosDeUmUsuario.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Eletrodoméstico(s) não encontrado(s) para o ID de usuário informado.");
		} else {
			return ResponseEntity.ok(listEletrodomesticosDeUmUsuario);
		}
	}
	@GetMapping(value = { "/endereco/{id}" })
	public ResponseEntity<?> consultarEletrodomesticoEnderecoId(
			@PathVariable int id){

		List<Eletrodomestico> listEletrodomestico = eletrodomesticoRepository.findByEnderecoId(id);

		if (listEletrodomestico.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Eletrodoméstico(s) não encontrado(s) para o ID de endereço informado.");
		} else {
			return ResponseEntity.ok(listEletrodomestico);
		}
	}
	@GetMapping(value = { "/usuarioendereco/{idUsr}/{idEnd}" })
	public ResponseEntity<?> consultarEletrodomesticoUsuarioIdEEnderecoId(
			@PathVariable int idUsr,
			@PathVariable int idEnd){

		List<Endereco> listEnderecosDeUmUsuario = enderecoRepository.findByUsuarioId(idUsr);

		List<Eletrodomestico> listEletrodomesticosDeUmUsuarioEEndereco = new ArrayList<>();
		for (Endereco endereco : listEnderecosDeUmUsuario){
			if (endereco.getId() == idEnd) {
				listEletrodomesticosDeUmUsuarioEEndereco.
						addAll(eletrodomesticoRepository.findByEnderecoId(endereco.getId()));
			}
		}

		if (listEletrodomesticosDeUmUsuarioEEndereco.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).
					body("Eletrodoméstico(s) não encontrado(s) para os IDs de usuário e endereço informados.");
		} else {
			return ResponseEntity.ok(listEletrodomesticosDeUmUsuarioEEndereco);
		}
	}
	@GetMapping(value = { "/" })
	public ResponseEntity<Collection<Eletrodomestico>> findAll() {
		var eletrodomestico = eletrodomesticoRepository.findAll();
		return ResponseEntity.ok(eletrodomestico);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluirEletrodomesticoId(@PathVariable int id) {

		Optional<Eletrodomestico> opEletrodomestico = eletrodomesticoRepository.findById(id);

		if (opEletrodomestico.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Eletrodoméstico não encontrado para exclusão.");
		} else {
			eletrodomesticoRepository.deleteById(id);
			return ResponseEntity.ok("Eletrodoméstico excluído com sucesso.");
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarEletrodomestico(
			@PathVariable int id,
			@RequestBody EletrodomesticoCadastroEAtualizacaoForm eletrodomesticoAtualizacaoForm) {

	    Map<Path, String> violacoesMap = validar(eletrodomesticoAtualizacaoForm);

	    if (!violacoesMap.isEmpty()) {
	        return ResponseEntity.badRequest().body(violacoesMap);
	    } else {
	        // brothres aqui verificamos  se o eletrodoméstico com o ID fornecido existe no banco de dados
	        Optional<Eletrodomestico> eletrodomesticoExistente = eletrodomesticoRepository.findById(id);

	        if (eletrodomesticoExistente.isEmpty()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Eletrodoméstico não encontrado.");
	        } else {
	            Eletrodomestico eletrodomestico = eletrodomesticoAtualizacaoForm.toEletrodomestico();

	            // Atualizamos as propriedades do eletrodoméstico existente com as do objeto recebido
	            Eletrodomestico eletrodomesticoAtualizado = eletrodomesticoExistente.get();

	            eletrodomesticoAtualizado.setNome(eletrodomestico.getNome());
	            eletrodomesticoAtualizado.setModelo(eletrodomestico.getModelo());
				eletrodomesticoAtualizado.setPotencia(eletrodomestico.getPotencia());
				eletrodomesticoAtualizado.setEndereco(eletrodomestico.getEndereco());
	            // Continue com a atualização de outras propriedades, se necessário

	            // Salvamos as alterações no banco de dados

				eletrodomesticoRepository.save(eletrodomesticoAtualizado);
	            return ResponseEntity.ok(eletrodomesticoAtualizado);
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



