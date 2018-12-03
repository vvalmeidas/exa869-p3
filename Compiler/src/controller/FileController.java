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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Util;

public class FileController {
	
	public static final String TEST_PATH = "teste";
	
	/**
	 * Construtor privado
	 */
	private FileController() {};
	
	/**
	 * Realiza a leitura de todos os arquivos .txt presentes na pasta entrada.
	 * @return Map no qual a chave � o nome do arquivo lido e o conte�do � uma string do arquivo lido.
	 * @throws IOException 
	 */
	public static Map<String, String> readFiles() throws IOException {		

        
		Map<String, String> result = new HashMap<>();
		File dir = new File(TEST_PATH);
		
		for(File file : dir.listFiles()) {
			if(file.getName().endsWith(".txt") && !file.getName().contains("resultado-")) {
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
	 * Salva os resultados da análise sintática
	 * @param fileName nome do arquivo
	 * @throws IOException erro de gravação
	 */
	public static void saveSyntacticResults(String fileName) throws IOException {
	    FileWriter writer = new FileWriter(TEST_PATH + "/resultado-" + fileName);
	    
	    if(Util.errors.size() == 0) {
	    	writer.write("SUCESSO!");
	    } else {
			for(String result: Util.errors) {
			  writer.write(result + "\n");
			}
	    }

		writer.close();
	}
	

}