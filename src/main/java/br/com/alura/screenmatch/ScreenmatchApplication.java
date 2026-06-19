package br.com.alura.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.screenmatch.model.DadosEpisodio;
import br.com.alura.screenmatch.model.DadosSerie;
import br.com.alura.screenmatch.services.ConsomeApi;
import br.com.alura.screenmatch.services.ConverteDadosJson;
import br.com.alura.screenmatch.services.IConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsomeApi consumir = new ConsomeApi();
		String url = "https://www.omdbapi.com/?apikey=ef9c0a95&t=gilmore_girls";
		IConverteDados conversor = new ConverteDadosJson();
		String json = consumir.obterDados(url);
		DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
		String urlEpisodio = "https://www.omdbapi.com/?apikey=ef9c0a95&t=gilmore_girls&season=1&episode=1";
		String jsonEpisodio = consumir.obterDados(urlEpisodio);
		DadosEpisodio episodio = conversor.obterDados(jsonEpisodio, DadosEpisodio.class);
		
		System.out.println(episodio);

	}

	

	
}
