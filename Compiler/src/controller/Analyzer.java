package controller;

import java.util.LinkedList;
import java.util.List;

import jdk.nashorn.internal.parser.TokenStream;
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
		if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("write")) {
				
			if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("(") ) {
					
				while(TokensFlow.hasNext() && TokensFlow.getNext().getTokenClass().equals("Identificador")  || TokensFlow.getNext().getTokenClass().equals("CadeCharacters")) {
					if(TokensFlow.getNext().getTokenClass().equals("Identifier")) {
						//aqui teria que chamar a função de array verification e de chamada a atributo
							
						if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(",")) {
						}
						else if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(")")) {
								
							if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(";")) {
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
						
					else if(TokensFlow.hasNext() && TokensFlow.getNext().getTokenClass().equals("CadeCharacters")) {
							
						if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(",")) {
								
						}
						else if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(")")) {
								
							if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(";")) {
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

	//<Commands>::= <If Statement><Commands> | <> | <While Statement><Commands> | <Read Statement><Commands>
    //              | <Attribution>';'<Commands> | <Write Statement><Commands> | <Return>';'
	public static boolean analiseCommands() {
		if(analiseIf()) {
			return analiseCommands();
		}
		else if(analiseWhile()) {
			return analiseCommands();
		}
		else if(analiseRead()) {
			return analiseCommands();
		}
		else if(analiseAttribution()) {
			if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(";")) {
				return analiseCommands();
			}
			return false;
		}
		else if(analiseWrite()) {
			return analiseCommands();
		}
		else if(analiseReturn()) {
			return (TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(";"));
		}
		return true; // se for vazio tem que tratar em quem chamou
	}
	
	//<While Statement> ::= 'while''(' <Expression> ')' '{' <Commands> '}'
	public static boolean analiseWhile() {
		if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("while")) {

			if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("(")) {
				if(analiseExpression()) {
					
					if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(")")) {
						
						if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("{")) {
							
							if(analiseCommands()) {
								
								if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("}")) {
									return true;
								}
								return false;
							}
							return false;
						}
						return false;
					}
					return false;
				}
				return false
			}
			return false;
		}
		return false;
	}
	
	//<Read Statement>   ::= 'read''(' <Reading_1> ')' ';'
	public static boolean analiseRead() {
		if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("read")) {
			
			if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("(")) {
				
				if(TokensFlow.hasNext() && AnalyzerSecondary.analiseReading1()) {

					if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(")")) {

						if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(";")) {
							return true;
						}
						return false;
					}
					return false;
				}
				return false;
			}
			return false;
		}
		
		return false;
	}
	
	// <Attribution> ::= <Increment>Identifier<Array Verification><Attr> | Identifier<Array Verification><Attr><Verif>
	public static boolean analiseAttribution() {
		if(AnalyzerSecondary.analiseIncrement()) {
			if(TokensFlow.hasNext() && TokensFlow.getNext().getTokenClass().equals("Identifier")) {
				if(AnalyzerSecondary.analiseArrayVerification()) {
					return AnalyzerSecondary.analiseAttr();
				}
			}
			return false;
		}
		else if(TokensFlow.hasNext() && TokensFlow.getNext().getTokenClass().equals("Identifier")) {
			if(AnalyzerSecondary.analiseArrayVerification()) {
				if(AnalyzerSecondary.analiseAttr()) {
					return AnalyzerSecondary.analiseVerif();
				}
			}
			return false;
		}	
		return false;
	}
	
	//<Return> ::= 'return' <Return1> 
	public static boolean analiseReturn() {
		if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("return")) {
			return AnalyzerSecondary.return1();
		}
		return false;
	}
	
}
