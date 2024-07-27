package com.veiculos.TabelaFipe.principal;

import java.util.Scanner;

import com.veiculos.TabelaFipe.service.ConsumoApi;

public class Principal {
  Scanner leitura = new Scanner(System.in);
  private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
  private ConsumoApi consumo = new ConsumoApi();

  public void exibirMenu() {
    var menu = """
        *** OPÇÕES ***
        Carro
        Moto
        Caminhão

        Digite uma das opções acima:
        """;

    System.out.println(menu);

    String opcao = leitura.nextLine();

    String endereco = null;

    if (opcao.toLowerCase().contains("carr")) {
      endereco = URL_BASE + "/carros/marcas";
    } else if (opcao.toLowerCase().contains("moto")) {
      endereco = URL_BASE + "/motos/marcas";
    } else if (opcao.toLowerCase().contains("cami")) {
      endereco = URL_BASE + "/caminhoes/marcas";
    }

    var json = consumo.obterDados(endereco);
    System.out.println(json);
  }
}
