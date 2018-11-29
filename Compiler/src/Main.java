import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import controller.Analyzer;
import controller.FileController;
import model.Token;
import model.TokensFlow;

public class Main {

	public static void main(String[] args) throws IOException {
		List<Token> tokensList = new LinkedList<Token>();
		
		Map<String, String> sourceFiles = FileController.readFiles();
		Iterator<String> iSource = sourceFiles.keySet().iterator();
		
		while(iSource.hasNext()) {
			String fileName = iSource.next();
			String sourceCode = sourceFiles.get(fileName);
			
			Lexer lexer = new Lexer();
			lexer.initialize(sourceCode);
			
			tokensList.addAll(lexer.getTokens());
		}
		
		//System.out.println(tokensList);
		
		TokensFlow.setTokensSet(tokensList);
		
		//System.out.println(Analyzer.analiseExpression());
		System.out.println(TokensFlow.isEmpty());
		
	}

}