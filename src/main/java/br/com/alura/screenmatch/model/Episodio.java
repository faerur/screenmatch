package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;


public class Episodio {
	private Integer temporada;
	private String titulo;
	private Integer numeroEpisodio;
	private Double avaliacao;
	private LocalDate dataLancamento;
	
	public Episodio(Integer numero, DadosEpisodio d) {
		this.temporada = numero;
		this.titulo = d.titulo();
		this.numeroEpisodio = d.numero();
		
		try {
			
		this.avaliacao = Double.valueOf(d.avaliacao());
		this.dataLancamento = LocalDate.parse(d.anoDeLancamento());
		} catch(NumberFormatException ex) {
			this.avaliacao = 0.0;
		} catch(DateTimeParseException date) {
			this.dataLancamento = null;
		}
	}
	
	
	public Integer getTemporada() {
		return temporada;
	}

	public String getTitulo() {
		return titulo;
	}

	public Integer getNumeroEpisodio() {
		return numeroEpisodio;
	}

	public Double getAvaliacao() {
		return avaliacao;
	}

	public LocalDate getDataLancamento() {
		return dataLancamento;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return """
				Temporada: %d
				Titulo: %s
				Numero do Episodio: %d
				Avaliação: %f
				Data de Lançamento: %s 
				""".formatted(temporada, titulo, numeroEpisodio, avaliacao, dataLancamento);
	}
}
