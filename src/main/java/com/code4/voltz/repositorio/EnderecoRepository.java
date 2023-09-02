package com.code4.voltz.repositorio;

import com.code4.voltz.dominio.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
    List<Endereco> findByUsuarioId(int id);
    List<Endereco> findByRuaAndNumero(String rua, String numero);
}
