package com.code4.voltz.repositorio;

import com.code4.voltz.dominio.Consumo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumoRepository extends JpaRepository<Consumo, Integer> {
    List<Consumo> findByEletrodomesticoId(int id);
}
