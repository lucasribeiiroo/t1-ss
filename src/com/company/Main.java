package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {

	public static void main(String[] args) throws IOException {

		// Le arquivo com texto cifrado
		Scanner sc = new Scanner(System.in);
		System.out.println("\nNome do arquivo: ");
		String arquivo = sc.nextLine();

		File input = new File("src/assets/" + arquivo + ".txt");
		BufferedReader in = new BufferedReader(new FileReader(input));
		String texto_cifrado = in.readLine();

		// Classe que realiza a decifragem
		Vigenere vigenere = new Vigenere(texto_cifrado);

		int tamanho_chave = vigenere.encontraTamanhoChave();

		// Busca caracteres da chave
		String chave = vigenere.encontraChave(tamanho_chave);

		// Decifra texto usando a chave encontrada
		String result = vigenere.decifra(chave);

		// Salva resultado no arquivo
		System.out.println("\nResultado salvo no arquivo: 'texto_claro.txt'");
		String outputfile = "texto_claro.txt";
		FileWriter out = new FileWriter(outputfile, false);
		out.write(result);
		out.close();
	}
}
