package com.veiculos.TabelaFipe.service;

public interface IConverteDados {
 <T> T obterDados(String json, Class<T> classe);
}
