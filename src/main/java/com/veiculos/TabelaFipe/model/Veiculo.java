package com.veiculos.TabelaFipe.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Veiculo(@JsonAlias("Valor") String valor, @JsonAlias("Combustivel") String combustivel,
    @JsonAlias("Marca") String marca, @JsonAlias("Modelo") String modelo, @JsonAlias("AnoModelo") Integer ano) {

}
