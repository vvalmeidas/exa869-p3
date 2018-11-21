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
		List<Token> tokens = new LinkedList<Token>();

		
/*		tokens.add(new Token("a", "if", 0));
		tokens.add(new Token("a", "(", 0));
		tokens.add(new Token("a", "a", 0));
		tokens.add(new Token("a", ")", 0));
		tokens.add(new Token("a", "then", 0));
		tokens.add(new Token("a", "{", 0));
		tokens.add(new Token("a", "while", 0));
		tokens.add(new Token("a", "(", 0));
		tokens.add(new Token("a", "true", 0));
		tokens.add(new Token("a", ")", 0));
		tokens.add(new Token("a", "{", 0));
		tokens.add(new Token("a", "a", 0));
		tokens.add(new Token("a", "=", 0));
		tokens.add(new Token("a", "1", 0));
		tokens.add(new Token("a", "+", 0));
		tokens.add(new Token("a", "2", 0));
		tokens.add(new Token("a", ";", 0));
		tokens.add(new Token("a", "}", 0));
		tokens.add(new Token("a", "}", 0));*/
		

		tokens.add(new Token("a", "while", 0));
		tokens.add(new Token("a", "(", 0));
		tokens.add(new Token("a", "true", 0));
		tokens.add(new Token("a", ")", 0));
		tokens.add(new Token("a", "{", 0));
		tokens.add(new Token("IDENTIFICADOR", "a", 0));
		tokens.add(new Token("OPERADOR_RELACIONAL", "=", 0));
		tokens.add(new Token("OPERADOR_RELACIONAL", "++", 0));
		tokens.add(new Token("NUMERO", "1", 0));
		tokens.add(new Token("OPERADOR_ARITMETICO", "*", 0));
		tokens.add(new Token("NUMERO", "3", 0));
		tokens.add(new Token("DELIMITADOR", ";", 0));
		tokens.add(new Token("a", "}", 0));
		
		//TODO: Tem que finalizar classmethods e global
		
		TokensFlow.setTokensSet(tokens);
		
		System.out.println(Analyzer.analiseWhile());
		
		//System.out.println(Analyzer.analiseIf());
	}

}
