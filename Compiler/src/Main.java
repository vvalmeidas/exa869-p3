import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import controller.Analyzer;
import controller.FileController;
import model.Token;
import model.TokensFlow;
import model.Util;

public class Main {


	public static void main(String[] args) throws IOException {		
		Map<String, String> sourceFiles = FileController.readFiles();
		Iterator<String> iSource = sourceFiles.keySet().iterator();
		
		while(iSource.hasNext()) {
			Util.errors = new LinkedList<String>();
			String fileName = iSource.next();
			String sourceCode = sourceFiles.get(fileName);
			
			Lexer lexer = new Lexer();
			lexer.initialize(sourceCode);
			TokensFlow.setTokensSet(lexer.getTokens());
			Analyzer.analiseGlobal();
			FileController.saveSyntacticResults(fileName);
		}
		
		System.out.println("Análise Sintatica Concluída!");
		
	}
	
}