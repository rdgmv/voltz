package com.code4.voltz.controller;

import com.code4.voltz.controller.form.ConsumoForm;
import com.code4.voltz.dominio.*;
import com.code4.voltz.repositorio.ConsumoRepository;
import com.code4.voltz.repositorio.EletrodomesticoRepository;
import com.code4.voltz.repositorio.EnderecoRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/consumos")
public class ConsumoController {
    @Autowired
    private Validator validator;

    @Autowired
    private ConsumoRepository consumoRepository;

    @Autowired
    private EletrodomesticoRepository eletrodomesticoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @PostMapping
    public ResponseEntity<?> cadastrarConsumo(@RequestBody ConsumoForm consumoForm) {
        Map<Path, String> violacoesMap = validar(consumoForm);

        if (!violacoesMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesMap);
        } else {

            Consumo consumo = consumoForm.toConsumo();

            Optional<Eletrodomestico> opEletrodomestico = eletrodomesticoRepository.
                    findById(consumo.getEletrodomestico().getId());

            if (opEletrodomestico.isEmpty()) {
                return ResponseEntity.badRequest().
                        body("Eletrodoméstico informado para o cadastramento do consumo não encontrado.");
            } else {
                consumo.setEletrodomestico(opEletrodomestico.get());
                consumo.calcularConsumoKwh();
                consumoRepository.save(consumo);
                return ResponseEntity.status(HttpStatus.CREATED).body(consumo);
            }
        }
    }

    @GetMapping(value = {"/id/{id}"})
    public ResponseEntity<?> consultarConsumoId(@PathVariable int id) {

        Optional<Consumo> opConsumo = consumoRepository.findById(id);

        if (opConsumo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Consumo não encontrado para o ID informado.");
        } else {
            return ResponseEntity.ok(opConsumo.get());
        }

    }

    @GetMapping(value = {"/eletrodomestico/{id}"})
    public ResponseEntity<?> consultarConsumoEletrodomesticoId(
            @PathVariable int id) {

        List<Consumo> listConsumo = consumoRepository.findByEletrodomesticoId(id);

        if (listConsumo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Consumo(s) não encontrado(s) para o ID de eletrodoméstico informado.");
        } else {
            return ResponseEntity.ok(listConsumo);
        }
    }

    @GetMapping(value = { "/endereco/{id}" })
    public ResponseEntity<?> consultarConsumoEnderecoId(
            @PathVariable int id){

        List<Eletrodomestico> listEletrodomestico = eletrodomesticoRepository.findByEnderecoId(id);
        if (listEletrodomestico.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Consumo(s) não encontrado(s) para o ID de endereço informado.");
        }

        List<Consumo> listConsumosDeUmEndereco = new ArrayList<>();
        for (Eletrodomestico eletrodomestico : listEletrodomestico){
            List<Consumo> listConsumosDeUmEletrodomestico =
                    consumoRepository.findByEletrodomesticoId(eletrodomestico.getId());
            listConsumosDeUmEndereco.addAll(listConsumosDeUmEletrodomestico);
        }

        if (listConsumosDeUmEndereco.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Consumo(s) não encontrado(s) para o ID de endereço informado.");
        } else {
            return ResponseEntity.ok(listConsumosDeUmEndereco);
        }
    }
    @GetMapping(value = { "/usuario/{id}" })
    public ResponseEntity<?> consultarConsumoUsuarioId(
            @PathVariable int id) {

        List<Endereco> listEndereco = enderecoRepository.findByUsuarioId(id);
        if (listEndereco.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Consumo(s) não encontrado(s) para o ID de usuário informado.");
        }

        List<Eletrodomestico> listEletrodomesticosDeUmUsuario = new ArrayList<>();
        for (Endereco endereco : listEndereco) {
            List<Eletrodomestico> listEletrodomesticosDeUmEndereco =
                    eletrodomesticoRepository.findByEnderecoId(endereco.getId());
            listEletrodomesticosDeUmUsuario.addAll(listEletrodomesticosDeUmEndereco);
        }

        if (listEletrodomesticosDeUmUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Consumo(s) não encontrado(s) para o ID de usuário informado.");
        }

        List<Consumo> listConsumosDeUmUsuario = new ArrayList<>();
        for (Eletrodomestico eletrodomestico : listEletrodomesticosDeUmUsuario) {
            List<Consumo> listConsumosDeUmEletrodomestico =
                    consumoRepository.findByEletrodomesticoId(eletrodomestico.getId());
            listConsumosDeUmUsuario.addAll(listConsumosDeUmEletrodomestico);
        }

        if (listConsumosDeUmUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Consumo(s) não encontrado(s) para o ID de endereço informado.");
        } else {
            return ResponseEntity.ok(listConsumosDeUmUsuario);
        }
    }
    @GetMapping(value = {"/"})
    public ResponseEntity<Collection<Consumo>> findAll() {
        var consumos = consumoRepository.findAll();
        return ResponseEntity.ok(consumos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirConsumoId(@PathVariable int id) {

        Optional<Consumo> opConsumo = consumoRepository.findById(id);

        if (opConsumo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumo não encontrado para exclusão.");
        } else {
            consumoRepository.deleteById(id);
            return ResponseEntity.ok("Consumo excluído com sucesso.");
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarConsumo(
            @PathVariable int id,
            @RequestBody ConsumoForm consumoForm) {

        Map<Path, String> violacoesMap = validar(consumoForm);

        if (!violacoesMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesMap);
        } else {

            Optional<Consumo> consumoExistente = consumoRepository.findById(id);

            if (consumoExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Consumo não encontrado.");
            } else {
                Consumo consumo = consumoForm.toConsumo();

                Consumo consumoAtualizado = consumoExistente.get();

                consumoAtualizado.setDataInicioApuracao(consumo.getDataInicioApuracao());
                consumoAtualizado.setDataFimApuracao(consumo.getDataFimApuracao());
                consumoAtualizado.setTempoUtilizacaoEmMinutos(consumo.getTempoUtilizacaoEmMinutos());

                Optional<Eletrodomestico> opEletrodomestico = eletrodomesticoRepository.
                        findById(consumo.getEletrodomestico().getId());

                if (opEletrodomestico.isEmpty()) {
                    return ResponseEntity.badRequest().
                            body("Eletrodoméstico informado para a atualização do consumo não encontrado.");
                }

                consumoAtualizado.setEletrodomestico(opEletrodomestico.get());
                consumoAtualizado.calcularConsumoKwh();

                consumoRepository.save(consumoAtualizado);
                return ResponseEntity.ok(consumoAtualizado);
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