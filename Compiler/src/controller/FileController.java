package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileController {
	
	public static final String TEST_PATH = "teste";
	
	/**
	 * Construtor privado
	 */
	private FileController() {};
	
	/**
	 * Realiza a leitura de todos os arquivos .txt presentes na pasta entrada.
	 * @return Map no qual a chave é o nome do arquivo lido e o conteúdo é uma string do arquivo lido.
	 * @throws IOException 
	 */
	public static Map<String, String> readFiles() throws IOException {		

        
		Map<String, String> result = new HashMap<>();
		File dir = new File(TEST_PATH);
		
		for(File file : dir.listFiles()) {
			if(file.getName().endsWith(".txt")) {
				BufferedReader bufferedReader  = new BufferedReader(
				    new InputStreamReader(new FileInputStream(file)));

				String line = bufferedReader.readLine();
				String string = new String();
				
				while(line != null) {
					string += line + System.lineSeparator();
					line = bufferedReader.readLine();
				}
				
				result.put(file.getName(), string);
			}
		}
		
		return result;
		
	}
	
	/**
	 * Salva o resultado da análise léxica na pasta saida.
	 * @param name nome do arquivo com o resultado da análise léxica
	 * @param results resultados da análise léxica
	 * @throws IOException 
	 */
	public static void saveFile(String name, String results) {
		try {
			Files.write(Paths.get(TEST_PATH + "/saida/resultado-" + name), results.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
