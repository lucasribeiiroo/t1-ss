package com.company;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnaliseFrequencias {
	public static double A = 14.634;
	public static double B = 1.043;
	public static double C = 3.882;
	public static double D = 4.992;
	public static double E = 12.570;
	public static double F = 1.023;
	public static double G = 1.303;
	public static double H = 0.781;
	public static double I = 6.186;
	public static double J = 0.397;
	public static double K = 0.015;
	public static double L = 2.779;
	public static double M = 4.738;
	public static double N = 4.446;
	public static double O = 9.735;
	public static double P = 2.523;
	public static double Q = 1.204;
	public static double R = 6.530;
	public static double S = 6.805;
	public static double T = 4.336;
	public static double U = 3.639;
	public static double V = 1.575;
	public static double W = 0.037;
	public static double X = 0.253;
	public static double Y = 0.006;
	public static double Z = 0.470;

	public static double[] FREQUENCIAS = { A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y,
			Z };

	private static Character[] ALFABETO = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	private static String texto_cifrado = "";

	public AnaliseFrequencias(String tc) {
		texto_cifrado = tc;
	}

	public static Character[] getALFABETO() {
		return ALFABETO;
	}

	// Divide o texto em uma matriz onde as colunas = tamanho da chave.
	// Cada coluna corresponde a um caractere que foi cifrado com um caractere da
	// chave.
	public Map<Integer, ArrayList<Character>> divideTexto(int tamanho_chave) {

		Map<Integer, ArrayList<Character>> matriz_texto_cifrado = new HashMap<>();

		// Arredonda valor da divisao do tamanho do texto / tamanho da chave
		int numero_linhas = (int) Math.ceil((double) texto_cifrado.length() / tamanho_chave);

		int index = 0;

		// Preenche matriz
		for (int i = 0; i < numero_linhas; i++) {

			matriz_texto_cifrado.put(i, new ArrayList<Character>());

			for (int j = 0; j < tamanho_chave && index < texto_cifrado.length(); j++) {
				// Para cada coluna da linha, adiciona a letra
				matriz_texto_cifrado.get(i).add(texto_cifrado.charAt(index));
				index++;
			}
		}
		return matriz_texto_cifrado;
	}

	private Map<Character, Integer> frequencia_letra = new HashMap<>();

	public Map<Character, Integer> getFrequencia_letra() {
		return frequencia_letra;
	}


	// Desloca a coluna a partir da letra.
	public String deslocaLetras(Character c, String coluna) {

		// Coluna com deslocamento
		StringBuilder nova_coluna = new StringBuilder();

		// Monta coluna da tabela de vigenere do caracter
		ArrayList<Character> ac = new ArrayList<>();
		int k = (int) c;
		for (int i = 0; i < getALFABETO().length; i++) {
			ac.add((char) k);
			if (k == 122)
				k = 97;
			else
				k++;
		}

		int index = 0;
		// Realiza deslocamento
		for (int i = 0; i < coluna.length(); i++) {
			for (int j = 0; j < ac.size(); j++) {
				if (ac.get(j) == coluna.charAt(i)) {
					index = j;
					break;
				}
			}
			nova_coluna.append(getALFABETO()[index]);
		}
		return nova_coluna.toString();
	}

	// Analisa cada coluna para descobrir a letra.
	public Character descobreCaractere(String coluna) {
		// Chi-Square: qual letra do alfabeto resulta na frequencia mais proxima a
		// frequencia da lıngua portuguesa
		Map<Character, Double> chiSquare = new HashMap<>();
		String coluna_analise = coluna;

		for (int i = 1; i < getALFABETO().length; i++) {

			double chi = 0;
			// Busca frequencia de cada letra da coluna
			frequenciaLetra(coluna_analise);

			// Para cada letra do alfabeto, calcula
			// o chi comparando com o caractere atual
			for (int j = 0; j < getALFABETO().length; j++) {
				chi += chiSquare(frequencia_letra.get(getALFABETO()[j]), (FREQUENCIAS[j] * coluna_analise.length()));
			}
			// Armazena letra e valor Chi-Square
			chiSquare.put(getALFABETO()[i - 1], chi);
			// Desloca letras na coluna conforme valor do caracter
			coluna_analise = deslocaLetras(getALFABETO()[i], coluna);
		}

		// Escolhe letra de menor valor
		Character letra = new Character('a');
		Double valor_minimmo = new Double(Double.MAX_VALUE);
		for (Character key : chiSquare.keySet()) {
			if (chiSquare.get(key)<valor_minimmo) {
				letra = key;
				valor_minimmo = chiSquare.get(key);
			}
		}
		return letra;
	}

	// Conta a frequencia de cada letra em uma coluna de caracteres
	public void frequenciaLetra(String coluna) {
		// Inicializa o Array de frequencia de caracteres 
		for (int i = 0; i < getALFABETO().length; i++) {
			frequencia_letra.put(getALFABETO()[i], 0);
		}
		// Calcula frequencia de cada letra na coluna
		for (int j = 0; j < coluna.length(); j++) {
			frequencia_letra.put(coluna.charAt(j), frequencia_letra.get(coluna.charAt(j)) + 1);
		}
	}

	// Teste Chi-Square para aproximar as frequências
	public static Double chiSquare(double freq_observada, double freq_esperada) {
		return Math.pow(freq_observada - freq_esperada, 2) / freq_esperada;
	}

}
