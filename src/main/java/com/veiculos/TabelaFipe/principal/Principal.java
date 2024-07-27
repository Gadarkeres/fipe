package com.veiculos.TabelaFipe.principal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.veiculos.TabelaFipe.model.Dados;
import com.veiculos.TabelaFipe.model.Modelos;
import com.veiculos.TabelaFipe.model.Veiculo;
import com.veiculos.TabelaFipe.service.ConsumoApi;
import com.veiculos.TabelaFipe.service.ConverteDados;

public class Principal {
  Scanner leitura = new Scanner(System.in);
  private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1";
  private ConsumoApi consumo = new ConsumoApi();
  private ConverteDados conversor = new ConverteDados();

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

    var marcas = conversor.obterLista(json, Dados.class);

    marcas.stream().sorted(Comparator.comparing(Dados::codigo)).forEach(System.out::println);

    System.out.println("digite o codigo da marca: ");
    String codigo = leitura.nextLine();

    endereco += "/" + codigo + "/modelos";
    json = consumo.obterDados(endereco);

    var modeloLista = conversor.obterDados(json, Modelos.class);
    System.out.println("\n Modelos dessa marca: ");
    modeloLista.modelos().stream().sorted(Comparator.comparing(Dados::codigo)).forEach(System.out::println);

    System.out.println("\n digite um trecho do nome do carro a ser buscado: ");
    String nomeVeiculo = leitura.nextLine();

    List<Dados> veiculosFiltrados = modeloLista.modelos().stream()
        .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase())).collect(Collectors.toList());
    System.out.println("\n modelos filtrados");

    veiculosFiltrados.forEach(System.out::println);

    System.out.println("Por favor digite o codigo do modelo: ");

    String codigoModelo = leitura.nextLine();

    endereco = endereco + "/" + codigoModelo + "/anos";

    json = consumo.obterDados(endereco);

    List<Dados> anos = conversor.obterLista(json, Dados.class);
    List<Veiculo> veiculos = new ArrayList<>();

    for (int i = 0 ; i < anos.size() ; i++) {
      var enderecoAnos = endereco + "/" + anos.get(i).codigo();
      json = consumo.obterDados(enderecoAnos);
      Veiculo veiculo = conversor.obterDados(json, Veiculo.class); 
      veiculos.add(veiculo);
  } 
System.out.println("\nTodos os veiculos filtrados com avaliação por ano: ");
veiculos.forEach(System.out::println);
}
}