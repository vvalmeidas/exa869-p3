package model;

import root.Analisador;

public class WriteRecognizer {
	private Analisador parser;
	private Token token;
	
	public WriteRecognizer(Token token, Analisador parser) {
		this.token = token;
		this.parser = parser;
	}
	
	public boolean analiseWrite() {
		if(token.getValue().equals("write")) {
			token = parser.nextToken();
			
			if(token.getValue().equals("(") ) {
				token = parser.nextToken();
				
				while(token.getTokenClass().equals("Identificador")  || token.getTokenClass().equals("CadeCharacters")) {
					if(token.getTokenClass().equals("Identifier")) {
						token = parser.nextToken();
						//aqui teria que chamar a função de array verification e de chamada a atributo
						
						if(token.getValue().equals(",")) {
							token = parser.nextToken();
						}
						else if(token.getValue().equals(")")) {
							token = parser.nextToken();
							
							if(token.getValue().equals(";")) {
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
					
					else if(token.getTokenClass().equals("CadeCharacters")) {
						token = parser.nextToken();
						
						if(token.getValue().equals(",")) {
							
							token = parser.nextToken();
						}
						else if(token.getValue().equals(")")) {
							token = parser.nextToken();
							
							if(token.getValue().equals(";")) {
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
	
}
