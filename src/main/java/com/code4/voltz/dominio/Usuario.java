package com.code4.voltz.dominio;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@NoArgsConstructor(force = true)
@Getter
@RequiredArgsConstructor
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    @NonNull
    private String nome;

    private LocalDate dataEntrada = LocalDate.now();

}
