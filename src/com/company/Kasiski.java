package com.company;

public class Kasiski {

	// Armazena distancias entre caracteres repetidos
	private static int distancias[] = new int[300];

	public int encontraTamanho(String texto_cifrado) {

		System.out.println("Tamanho do texto cifrado: " + texto_cifrado.length());

		String comparador_caracteres = "";
		int cont = 0;
		int tamanho_palavra = 12;
		for (int i = 0; i < (texto_cifrado.length() - tamanho_palavra); i++) {
			// procura trechos de string que se repetem pelo texto
			comparador_caracteres = texto_cifrado.substring(i, i + tamanho_palavra);
			
			for (int j = i + 1; j < texto_cifrado.length() - tamanho_palavra; j++) {
				if (comparador_caracteres.equals(texto_cifrado.substring(j, j + tamanho_palavra)) && cont < 300) {
					distancias[cont] = j - i;
					cont++;
				}
			}
		}
		// realiza busca do tamanho da chave com algoritmo euclidiano aplicado as distancias
		int tamanho_chave = alg_euclidiano(distancias[0], distancias[1]);

		if (distancias.length > 2) {
			for (int k = 2; k < distancias.length; k++) {
				tamanho_chave = alg_euclidiano(tamanho_chave, distancias[k]);
			}
		}
		return tamanho_chave;
	}
	
	// Algoritmo Euclidiano: maior divisor comum de dois números
    public static int alg_euclidiano(int p, int q) {
        if (q == 0) return p;
        else return alg_euclidiano(q, p % q);
    }

}