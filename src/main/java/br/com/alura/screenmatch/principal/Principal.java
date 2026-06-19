package br.com.alura.screenmatch.principal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.services.ConsomeApi;
import br.com.alura.screenmatch.services.ConverteDadosJson;
import br.com.alura.screenmatch.services.IConverteDados;

public class Principal {
	public static final String URL = "https://www.omdbapi.com/?apikey=";
	public static final String API_KEY = "ef9c0a95";
	private IConverteDados conversor = new ConverteDadosJson();
	private ConsomeApi consumir = new ConsomeApi();
	private Scanner scanner = new Scanner(System.in);
	
	public void obterDados() {
		System.out.println("Informe a série que deseja consultar: ");
		String serie = scanner.nextLine();
		
		String json = consumir.obterDados(URL + API_KEY + "&t=" + serie);
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		List<DadosTemporada> temporadas = new ArrayList<>(); 
		
		for(int i = 1; i < dados.totalTemporadas(); i++) {
			String serieJson = consumir.obterDados(URL + API_KEY + "&t=" + serie + "&season=" + i);
			DadosTemporada temporada = conversor.obterDados(serieJson, DadosTemporada.class);
			temporadas.add(temporada);
		}
		
//		for(int i = 0; i < temporadas.size(); i++ ) {
//			DadosTemporada temporada = temporadas.get(i);
//			for(int k = 0; k < temporada.episodios().size(); k++) {
//				System.out.println(temporada.episodios().get(k));
//			}
//		}
		
		temporadas.forEach(System.out::println);

        temporadas.forEach(t -> t.episodios().forEach(e -> System.out.println(e.titulo())));
		
		
		json = consumir.obterDados("https://www.omdbapi.com/?apikey=ef9c0a95&t=icarly&season=1&episode=1");
		DadosEpisodio episodio = conversor.obterDados(json, DadosEpisodio.class);

		json = consumir.obterDados("https://www.omdbapi.com/?apikey=ef9c0a95&t=icarly&season=1");
		DadosTemporada temporada = conversor.obterDados(json, DadosTemporada.class);

		System.out.println(dados);
		System.out.println(episodio);
		System.out.println(temporada);
	}
}
