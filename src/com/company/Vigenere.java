package com.company;

import java.util.ArrayList;
import java.util.Map;

public class Vigenere {

	private static String texto_cifrado = "";
	private static AnaliseFrequencias analise_frequencias;

	public Vigenere(String tc) {
		texto_cifrado = tc;
		analise_frequencias = new AnaliseFrequencias(texto_cifrado);
	}

	public static String encontraChave(int tamanho_chave) {
		// Divide texto cifrado em uma matriz com colunas = tamanho da chave
		Map<Integer, ArrayList<Character>> matriz_texto_cifrado = analise_frequencias.divideTexto(tamanho_chave);

		StringBuilder chave = new StringBuilder();

		// Encontra o caracter da chave a partir de cada coluna da matriz
		for (int i = 0; i < tamanho_chave; i++) {
			StringBuilder coluna = new StringBuilder();

			for (int j = 0; j < matriz_texto_cifrado.size()-1; j++) {
				coluna.append(matriz_texto_cifrado.get(j).get(i));
			}
			chave.append(analise_frequencias.descobreCaractere(coluna.toString()));
		}
		System.out.println("\nChave encontrada: " + chave.toString() + "\n");
		return chave.toString();
	}

	// Sabendo qual a chave, decifra vigenere pelas cifras de cesar simples agora conhecidas
	public static String decifra(String chave) {
		StringBuilder texto_claro = new StringBuilder();
		char letra_decifrada;
		int deslocamento = 0;

		for (int i = 0; i < texto_cifrado.length(); i++) {
			// Converte para range 0-25. D(Ci) = (Ci - Ki) mod 26
			deslocamento = texto_cifrado.charAt(i) - chave.charAt(i % chave.length());
			if (deslocamento < 0)
				deslocamento += analise_frequencias.getALFABETO().length;

			// Converte para letra (ASCII)
			letra_decifrada = (char) (deslocamento + 'a');
			texto_claro.append(letra_decifrada);
		}
		return texto_claro.toString();
	}

	public static int encontraTamanhoChave() {
		Kasiski kasiski = new Kasiski();
		int tamanho = kasiski.encontraTamanho(texto_cifrado);
		System.out.println("Tamanho da chave: " + tamanho);
		return tamanho;
	}

}
