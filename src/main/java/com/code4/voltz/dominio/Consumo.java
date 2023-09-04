package com.code4.voltz.dominio;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor(force = true)
@Getter
@RequiredArgsConstructor
@Entity
public class Consumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Setter
    @NonNull
    private LocalDate dataInicioApuracao;
    @Setter
    @NonNull
    private LocalDate dataFimApuracao;
    @Setter
    @NonNull
    private int tempoUtilizacaoEmMinutos;

    private BigDecimal valorConsumoKwh;
    @ManyToOne
    @JoinColumn(name = "eletrodomestico_id")
    @Setter
    @NonNull
    private Eletrodomestico eletrodomestico;

    private LocalDate dataEntrada = LocalDate.now();

    public void calcularConsumoKwh(){
        valorConsumoKwh = BigDecimal.valueOf((tempoUtilizacaoEmMinutos / 60) * eletrodomestico.getPotencia() / 1000);
    }
}
