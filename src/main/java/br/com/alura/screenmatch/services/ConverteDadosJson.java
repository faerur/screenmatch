package br.com.alura.screenmatch.services;

import com.google.gson.Gson;

import tools.jackson.databind.ObjectMapper;

public class ConverteDadosJson implements IConverteDados{
	private ObjectMapper mapper = new ObjectMapper();
	//private Gson gson = new Gson();
	
	@Override
	public <T> T obterDados(String json, Class<T> classe) {
		//return gson.fromJson(json, classe);
		return mapper.readValue(json, classe);
	}
	
	
}
