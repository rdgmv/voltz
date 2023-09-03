package com.code4.voltz.repositorio;

import com.code4.voltz.dominio.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer>{
    List<Pessoa> findByNomeAndDataNascimento(String nome, LocalDate dataNascimento);
    List<Pessoa> findByEnderecoId(int id);

}
