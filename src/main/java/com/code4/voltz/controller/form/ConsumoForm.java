package com.code4.voltz.controller.form;

import com.code4.voltz.dominio.Consumo;
import com.code4.voltz.dominio.Eletrodomestico;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class ConsumoForm {
    @NotNull(message = "Campo data início apuração não pode ser nulo.")
    @Past(message = "Data inicial da apuração deve ser anterior à data atual.")
    @JsonProperty
    private LocalDate dataInicioApuracao;
    @NotNull(message = "Campo data fim apuração não pode ser nulo.")
    @Past(message = "Data final da apuração deve ser anterior à data atual.")
    @JsonProperty
    private LocalDate dataFimApuracao;
    @NotNull(message = "Campo tempo de utilização não pode ser nulo.")
    @Min(value = 0, message = "Campo tempo de utilização deve maior ou igual a zero.")
    @Max(value = 9999999, message = "Campo tempo de utilização não deve ser maior que 9.999.999.")
    @JsonProperty
    private int tempoUtilizacaoEmMinutos;
    @NotNull(message = "Campo ID do eletrodoméstico não pode ser nulo.")
    @JsonProperty
    private Eletrodomestico eletrodomestico;

    public Consumo toConsumo() {
        return new Consumo(dataInicioApuracao, dataFimApuracao, tempoUtilizacaoEmMinutos, eletrodomestico);
    }
}
