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
	EletrodomesticoRepository eletrodomesticoRepository; 

	@Autowired
	private Validator validator;

	@PostMapping
	public ResponseEntity<?> cadastrarEletrodomestico(@RequestBody EletrodomesticoCadastroEAtualizacaoForm eletrodomesticoCadastroForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoCadastroForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {

			Eletrodomestico eletrodomestico = eletrodomesticoCadastroForm.toEletrodomestico();

			eletrodomesticoRepository.save(eletrodomestico);


			return ResponseEntity.status(HttpStatus.CREATED).body(eletrodomestico);
		}

	}

	@GetMapping
	public ResponseEntity<?> consultaEletrodomestico(@RequestBody EletrodomesticoConsultaEExclusaoForm eletrodomesticoConsultaForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoConsultaForm);

		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Eletrodomestico eletrodomestico = eletrodomesticoConsultaForm.toEletrodomestico();


			Eletrodomestico eletrodomesticoEncontrado = eletrodomesticoRepository.findByNomeAndModelo(eletrodomestico.getNome(), eletrodomestico.getModelo());
			
			if (eletrodomesticoEncontrado == null ) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Eletrodoméstico não encontrado.");
			} else {
				return ResponseEntity.ok(eletrodomesticoEncontrado);
			}
		}

	}

	@GetMapping(value = { "/" })
	public ResponseEntity<Collection<Eletrodomestico>> findAll() {
		var eletrodomestico = eletrodomesticoRepository.findAll();

		return ResponseEntity.ok(eletrodomestico);
	}

	@DeleteMapping
	public ResponseEntity<?> excluirEletrodomestico(@RequestBody EletrodomesticoConsultaEExclusaoForm eletrodomesticoExclusaoForm) {
		Map<Path, String> violacoesMap = validar(eletrodomesticoExclusaoForm);
		
		if (!violacoesMap.isEmpty()) {
			return ResponseEntity.badRequest().body(violacoesMap);
		} else {
			Eletrodomestico eletrodomestico = eletrodomesticoExclusaoForm.toEletrodomestico();

			Eletrodomestico eletrodomesticoEncontrado = eletrodomesticoRepository.findByNomeAndModelo(eletrodomestico.getNome(), eletrodomestico.getModelo());

			if (eletrodomesticoEncontrado == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Eletrodoméstico não encontrado.");
			} else {
				eletrodomesticoRepository.delete(eletrodomesticoEncontrado);
				return ResponseEntity.ok("Eletrodoméstico excluído com sucesso.");
			}
		}

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> atualizarEletrodomestico(@PathVariable int id, @RequestBody EletrodomesticoCadastroEAtualizacaoForm eletrodomesticoAtualizacaoForm) {
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
	            // Continue com a atualização de outras propriedades, se necessário

	            // Salvamos as alterações no banco de dados
	            Eletrodomestico eletrodomesticoSalvo = eletrodomesticoRepository.save(eletrodomesticoAtualizado);
	            return ResponseEntity.ok(eletrodomesticoSalvo);
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



