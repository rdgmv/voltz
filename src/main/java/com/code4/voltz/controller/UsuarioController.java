package com.code4.voltz.controller;

import com.code4.voltz.controller.form.UsuarioForm;
import com.code4.voltz.dominio.Usuario;
import com.code4.voltz.repositorio.UsuarioRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private Validator validator;

    @Autowired
    private UsuarioRepository usuarioRepository;
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

    private <T> Map<Path, String> validar(T dto) {
        Set<ConstraintViolation<T>> violacoes = validator.validate(dto);
        Map<Path, String> violacoesMap = violacoes.stream()
                .collect(Collectors.toMap(violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()));
        return violacoesMap;
    }
}
