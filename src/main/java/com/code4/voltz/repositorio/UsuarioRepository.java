package com.code4.voltz.repositorio;

import com.code4.voltz.dominio.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
