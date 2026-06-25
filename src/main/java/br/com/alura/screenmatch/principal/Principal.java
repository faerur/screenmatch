package br.com.alura.screenmatch.principal;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.model.DadosTemporada;
import br.com.alura.screenmatch.model.Episodio;
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
		List<DadosEpisodio> listaEpisodios = temporadas.stream().flatMap(t -> t.episodios().stream()).collect(Collectors.toList());
		
		listaEpisodios.stream()
		.filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
		.sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
		.limit(5)
		.forEach(System.out::println);
		
        List<Episodio> episodios = temporadas.stream()
        		.flatMap(t -> t.episodios().stream().map(d -> new Episodio(t.numero(), d))
        				).collect(Collectors.toList());
		
        System.out.println("A partir de que ano você deseja ver os episódios? ");
        var ano = scanner.nextInt();
        scanner.nextLine();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
		episodios.stream()
		.filter(e ->  e.getDataLancamento() != null && e.getDataLancamento().isAfter(LocalDate.of(ano, 1,1)))
		.sorted(Comparator.comparingDouble(Episodio::getAvaliacao).reversed())
		.limit(5)
		.forEach(
				e -> System.out.println(
						"Temporada: " + e.getTemporada() +
							"\nEpisódio: " + e.getTitulo() +
							"\nAvaliação: " + e.getAvaliacao() + 
							"\nData lançamento: " + e.getDataLancamento().format(formatador)
						)
				);
	}
}
