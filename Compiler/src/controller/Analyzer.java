package controller;

import java.util.LinkedList;
import java.util.List;

import model.Token;
import model.TokensFlow;

public class Analyzer {
	private Token token;
	
	private static LinkedList<String> firstElse = new LinkedList<String>() {{
		add("else");
	}};
	
	public Token nextToken() {
		return null;
	}
	
	public static boolean analiseExpression() { //<Expression> ::= <And Exp> <A>
		if(AnalyzerSecondary.andExp()) {
			nextToken();
			if(AnalyzerSecondary.a()) {
				return true;
			}
			return false;
		}
		
		return false;
	}

	public static boolean analiseWrite() {
		if(TokensFlow.getNext().getValue().equals("write")) {
			nextToken();
				
			if(TokensFlow.getNext().getValue().equals("(") ) {
				token = nextToken();
					
				while(TokensFlow.getNext().getTokenClass().equals("Identificador")  || TokensFlow.getNext().getTokenClass().equals("CadeCharacters")) {
					if(TokensFlow.getNext().getTokenClass().equals("Identifier")) {
						token = nextToken();
						//aqui teria que chamar a função de array verification e de chamada a atributo
							
						if(TokensFlow.getNext().getValue().equals(",")) {
							token = nextToken();
						}
						else if(TokensFlow.getNext().getValue().equals(")")) {
							token = nextToken();
								
							if(TokensFlow.getNext().getValue().equals(";")) {
								return true;
							}
							else {
								return false;
							}
						}
						else {
							return false;
						}
					}
						
					else if(TokensFlow.getNext().getTokenClass().equals("CadeCharacters")) {
						token = nextToken();
							
						if(TokensFlow.getNext().getValue().equals(",")) {
								
							token = nextToken();
						}
						else if(TokensFlow.getNext().getValue().equals(")")) {
							token = nextToken();
								
							if(TokensFlow.getNext().getValue().equals(";")) {
								return true;
							}
							else {
								return false;
							}
						}
						else {
							return false;
						}
					}			
				}								
			}
			return false;
		}
		return false;
	}
	
	
	public static boolean analiseIf() {
		if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("if")) {
			if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("(")) {

				if(TokensFlow.hasNext() && true) { //analiseExpression() 
					TokensFlow.getNext(); //TIRAR ISSO AQUI

					if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(")")) {

						if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("then")) {

							if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("{")) {

								if(TokensFlow.hasNext() && true) { //commands
									System.out.println(TokensFlow.getNext().getValue());; //TIRAR ISSO AQUI

									if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("}")) {

										if(TokensFlow.hasNext() && firstElse.contains(TokensFlow.seeActual().getValue())) {
											return AnalyzerSecondary.analiseElse();
										} 
										
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		
		return false;
	}

	
	public static boolean analiseCommands() {
		return true;
	}

}
