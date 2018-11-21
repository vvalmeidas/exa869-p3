package controller;

import java.util.LinkedList;
import java.util.List;

import model.First;
import model.Token;
import model.TokensFlow;

public class Analyzer {
	
	//<Global> ::= <Constant Declaration> <Class Declaration> <More Classes>
	public static boolean analiseGlobal() {
		if(TokensFlow.hasNext() && First.check("ConstantDeclaration", TokensFlow.getToken())) {
			if(analiseConstantDeclaration()) {
				
			}
		}
		
		return false;
	}
	
	//<Class Declaration> ::= 'class' <Class Identification>
	public static boolean analiseClassDeclaration() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("class")) {
			TokensFlow.next();
			
			if(AnalyzerSecondary.analiseClassIdentification()) {
				return true;
			}
		}
		
		return false;
	}

	//<Constant Declaration> ::= 'const' '{' <Constants> '}' | <> 
	public static boolean analiseConstantDeclaration() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("const")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
				TokensFlow.next();
				
				if(AnalyzerSecondary.analiseConstants()) {
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
						TokensFlow.next();
						
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	
	//<Variable Declaration> ::= 'variables' '{' <Variable> '}' | <> 
	public static boolean analiseVariableDeclaration() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("variables")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
				TokensFlow.next();
				
				if(AnalyzerSecondary.analiseVariable()) {
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
						TokensFlow.next();
						return true;
					}
				}
				
			}
			
		}
		
		return false;
	}
	
	
	public static boolean analiseExpression() { //<Expression> ::= <And Exp> <A>		

		if(TokensFlow.hasNext() && First.check("AndExp", TokensFlow.getToken())) {

			if(AnalyzerSecondary.analiseAndExp()) {

				if(TokensFlow.hasNext() && First.check("AndExp", TokensFlow.getToken())) {
					return AnalyzerSecondary.analiseA();
				}

				return true;
			}
			
			return true;
		} else if(TokensFlow.hasNext() && First.check("A", TokensFlow.getToken())) {

			return AnalyzerSecondary.analiseA();
		}
		
		return false;
		
	}

	public static boolean analiseWrite() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("write")) {
			TokensFlow.next();
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(") ) {
				TokensFlow.next();	
				if(AnalyzerSecondary.analiseWriting1()) {
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
						TokensFlow.next();	
						return TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";");
					}
				}
				return false;
			}
			return false;
		}
		return false;
	}
	
	
	public static boolean analiseIf() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("if")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
				TokensFlow.next();
				
				if(TokensFlow.hasNext() && true) { //analiseExpression() 
					TokensFlow.next(); //TIRAR ISSO AQUI

					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
						TokensFlow.next();
						
						if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("then")) {
							TokensFlow.next();
							
							if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
								TokensFlow.next();
								
								if(TokensFlow.hasNext() && analiseCommands()) { //commands
									
									if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
										TokensFlow.next();
										
										if(TokensFlow.hasNext() && First.check("Else", TokensFlow.getToken())) {
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
		} else if(analiseWhile()) {
			return analiseCommands();
		} else if(analiseRead()) {
			return analiseCommands();
		} else if(analiseAttribution()) {
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
				TokensFlow.next();
				
				if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {

					return analiseCommands();
				}
				
				return true;
				
			}
			
			return false;
		} else if(analiseWrite()) {
			return analiseCommands();
		} else if(analiseReturn()) {
			return (TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";"));
		}

		return false;
	}
	
	//<While Statement> ::= 'while''(' <Expression> ')' '{' <Commands> '}'
	public static boolean analiseWhile() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("while")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
				TokensFlow.next();
				
				if(analiseExpression()) {
					
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
						TokensFlow.next();
						
						if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
							TokensFlow.next();
							
							System.out.println(TokensFlow.getToken().getTokenClass());
							
							if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
								if(analiseCommands()) {
									
									System.out.println(TokensFlow.getToken());
									if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
										TokensFlow.next();
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
	
	//<Read Statement>   ::= 'read''(' <Reading_1> ')' ';'
	public static boolean analiseRead() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("read")) {
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
				
				if(TokensFlow.hasNext() && AnalyzerSecondary.analiseReading1()) {

					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {

						if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
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
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
				TokensFlow.next();
				
				if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
					if(AnalyzerSecondary.analiseArrayVerification()) {
						
						if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
							return AnalyzerSecondary.analiseAttr();
						}
						
					}
				} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					return AnalyzerSecondary.analiseAttr();
				} 
				
				return true;
			}
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) { //segunda regra
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				if(AnalyzerSecondary.analiseArrayVerification()) {
					
					if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
						if(AnalyzerSecondary.analiseAttr()) {
							return AnalyzerSecondary.analiseVerif();
						}
					}
					
				}
				
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				
				if(AnalyzerSecondary.analiseAttr()) {
					return AnalyzerSecondary.analiseVerif();
				}
				
			} else {
				return AnalyzerSecondary.analiseVerif();
			}
			
		}
		
		
		return false;
	}
	
	//<Return> ::= 'return' <Return1> 
	public static boolean analiseReturn() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("return")) {
			TokensFlow.next();
			
			return AnalyzerSecondary.return1();
		}
		return false;
	}
	
	//<Write Statement> ::= 'write''('<Writing_1>')' ';'
	public static boolean analiseWriteStatement() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("write")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
				TokensFlow.next();
				
				if(TokensFlow.hasNext() && AnalyzerSecondary.analiseWriting1()) {
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
						TokensFlow.next();
						
						if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
							TokensFlow.next();
							
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
	
}
