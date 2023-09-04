package com.code4.voltz.controller;

import com.code4.voltz.controller.form.UsuarioForm;
import com.code4.voltz.dominio.Endereco;
import com.code4.voltz.dominio.Usuario;
import com.code4.voltz.repositorio.EnderecoRepository;
import com.code4.voltz.repositorio.UsuarioRepository;
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
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private Validator validator;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;
    @PostMapping
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioForm usuarioForm) {
        Map<Path, String> violacoesMap = validar(usuarioForm);

        if (!violacoesMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesMap);
        } else {

            Usuario usuario = usuarioForm.toUsuario();

            usuarioRepository.save(usuario);

            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        }
    }

    @GetMapping(value = { "/id/{id}" })
    public ResponseEntity<?> consultarUsuarioId(@PathVariable int id){

        Optional<Usuario> opUsuario = usuarioRepository.findById(id);

        if (opUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).
                    body("Usuário não encontrado para o ID informado.");
        } else {
            return ResponseEntity.ok(opUsuario.get());
        }

    }

    @GetMapping
    public ResponseEntity<?> consultarUsuarioNome(
            @RequestBody UsuarioForm usuarioForm) {
        Map<Path, String> violacoesMap = validar(usuarioForm);

        if (!violacoesMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesMap);
        } else {
            Usuario usuario = usuarioForm.toUsuario();

            List<Usuario> listUsuario =
                    usuarioRepository.findByNome(usuario.getNome());

            if (listUsuario.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).
                        body("Usuário não encontrado para o nome informado.");
            } else {
                return ResponseEntity.ok(listUsuario);
            }
        }
    }
    @GetMapping(value = { "/" })
    public ResponseEntity<Collection<Usuario>> findAll() {
        var usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(usuarios);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluirUsuarioId(@PathVariable int id) {

        Optional<Usuario> opUsuario = usuarioRepository.findById(id);

        if (opUsuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado para exclusão.");
        } else {
            List<Endereco> listEndereco = enderecoRepository.findByUsuarioId(id);
            if (listEndereco.isEmpty()){
                usuarioRepository.deleteById(id);
                return ResponseEntity.ok("Usuário excluído com sucesso.");
            } else {
                return ResponseEntity.badRequest().body("Exclusão não permitida. Usuário possui endereço(s)" +
                        " vinculado(s). Para exclui-lo, desfaça primeiramente o(s) vínculo(s).");
            }
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarPessoa(
            @PathVariable int id,
            @RequestBody UsuarioForm usuarioForm) {

        Map<Path, String> violacoesMap = validar(usuarioForm);

        if (!violacoesMap.isEmpty()) {
            return ResponseEntity.badRequest().body(violacoesMap);
        } else {

            Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

            if (usuarioExistente.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            } else {
                Usuario usuario = usuarioForm.toUsuario();

                Usuario usuarioAtualizado = usuarioExistente.get();
                usuarioAtualizado.setNome(usuario.getNome());

                usuarioRepository.save(usuarioAtualizado);
                return ResponseEntity.ok(usuarioAtualizado);
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
