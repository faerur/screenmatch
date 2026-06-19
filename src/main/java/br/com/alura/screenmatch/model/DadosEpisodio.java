package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DadosEpisodio (@JsonAlias("Episode") int numero, @JsonAlias("Title") String titulo, @JsonAlias("Year") int anoDeLancamento){

}
