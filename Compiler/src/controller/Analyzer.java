package controller;

import java.util.LinkedList;
import java.util.List;

import model.First;
import model.Token;
import model.TokensFlow;
import model.Util;

public class Analyzer {
	
	//<Global> ::= <Constant Declaration> <Class Declaration> <More Classes>
	public static void analiseGlobal() {
		if(TokensFlow.hasNext() && First.check("ConstantDeclaration", TokensFlow.getToken())) {
			analiseConstantDeclaration();
			analiseClassDeclaration();

			if(TokensFlow.hasNext() && First.check("MoreClasses", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseMoreClasses();
				return;
			} else {
				return;
			}
			
		} else {
			analiseClassDeclaration();
			if(TokensFlow.hasNext() && First.check("MoreClasses", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseMoreClasses();
				return;
			} else {
				return;
			}
		}
		
	}
	
	//<Class Declaration> ::= 'class' <Class Identification>
	public static void analiseClassDeclaration() {
		Util.handleTerminal("class", true, false);
		AnalyzerSecondary.analiseClassIdentification();
		return;
	}

	//<Constant Declaration> ::= 'const' '{' <Constants> '}' | <> 
	public static void analiseConstantDeclaration() {		
		Util.handleTerminal("const", true, false);
		
		Util.handleTerminal("{", true, false);
		
		AnalyzerSecondary.analiseConstants();
		
		Util.handleTerminal("}", true, false);
		
	}
	
	
	//<Variable Declaration> ::= 'variables' '{' <Variable> '}' | <> 
	public static void analiseVariableDeclaration() {
		Util.handleTerminal("variables", true, false);
		
		Util.handleTerminal("{", true, false);

		AnalyzerSecondary.analiseVariable();

		Util.handleTerminal("}", true, false);
		
	}
	
	//<Method Declaration> ::= 'method' <Type> Identifier '(' <Parameter Declaration> ')' '{' <Variable Declaration> <Commands> '}' <More Methods>
	public static void analiseMethodDeclaration() {
		Util.handleTerminal("method", true, false);
		
		AnalyzerSecondary.analiseType();
		
		Util.handleTerminal("IDENTIFICADOR", false, false);
		
		Util.handleTerminal("(", true, false);
		
		
		if(TokensFlow.hasNext() && First.check("ParameterDeclaration", TokensFlow.getToken())) {

			AnalyzerSecondary.analiseParameterDeclaration();
			
			Util.handleTerminal(")", true, false);
			
			Util.handleTerminal("{", true, false);

			if(TokensFlow.hasNext() && First.check("VariableDeclaration", TokensFlow.getToken())) {

				analiseVariableDeclaration();
				
				if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
					analiseCommands();
					Util.handleTerminal("}", true, false);										
					if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
						AnalyzerSecondary.analiseMoreMethods();
						return;
					} else {
						return;
					}
				
					} else {
						Util.handleTerminal("}", true, false);
							
						if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
							AnalyzerSecondary.analiseMoreMethods();
							return;
						} else {
							return;
						}
					}
			}
		
			else if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				Util.handleTerminal("}", true, false);
				if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
					AnalyzerSecondary.analiseMoreMethods();
					return;
				} else {
					return;
				}
			} else {
				Util.handleTerminal("}", true, false);
				
				if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
					AnalyzerSecondary.analiseMoreMethods();
					return;
				} else {
					return;
				}
			}
		} else {
			Util.handleTerminal(")", true, false);
			Util.handleTerminal("{", true, false);

			if(TokensFlow.hasNext() && First.check("VariableDeclaration", TokensFlow.getToken())) { 
				analiseVariableDeclaration();
				if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
					analiseCommands();
					Util.handleTerminal("}", true, false);
								
					if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
						AnalyzerSecondary.analiseMoreMethods();
						return;
					} else {
						return;
					}
					
				} else {
					Util.handleTerminal("}", true, false);
					
					if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
						AnalyzerSecondary.analiseMoreMethods();
						return;
					} else {
						return;
					}
				}
				
			} else if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				Util.handleTerminal("}", true, false);

				if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
					AnalyzerSecondary.analiseMoreMethods();
					return;
				} else {
					return;
				}
			} else {
				Util.handleTerminal("}", true, false);
				if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
					AnalyzerSecondary.analiseMoreMethods();
					return;
				} else {
					return;
				}
			}
		}
	}
	
	//<Expression> ::= <Add Exp><Relational Exp>
	public static void analiseExpression() { 
		AnalyzerSecondary.analiseAddExp();
		AnalyzerSecondary.analiseRelationalExp();
	}

	//<Write Statement> ::= 'write''('<Writing_1>')' ';'
	public static void analiseWriteStatement() {
		Util.handleTerminal("write", true, false);
		Util.handleTerminal("(", true, false);
		AnalyzerSecondary.analiseWriting1();
		Util.handleTerminal(")", true, false);
		Util.handleTerminal(";", true, false);
	}
	
	//<If Statement> ::= 'if''('<Expression>')' 'then' '{'<Commands>'}'<Else Statement>
	public static void analiseIf() {
		Util.handleTerminal("if", true, false);
		Util.handleTerminal("(", true, false);
		analiseExpression();
		Util.handleTerminal(")", true, false);
		Util.handleTerminal("then", true, false);
		Util.handleTerminal("{", true, false);

		if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
			analiseCommands();
			Util.handleTerminal("}", true, false);
			if(TokensFlow.hasNext() && First.check("ElseStatement", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseElseStatement();
				return;
			} else {
				return;
			}

		} else {
			Util.handleTerminal("}", true, false);
			if(TokensFlow.hasNext() && First.check("ElseStatement", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseElseStatement();
				return;
			} else {
				return;
			}
		}
	}
	
	//COMO IDENTIFICAR O ERRO AQUI??????
	//<Commands>::= <If Statement><Commands> | <> | <While Statement><Commands> | <Read Statement><Commands>
    //              | <Attribution>';'<Commands> | <Write Statement><Commands> | <Return>';'
	public static void analiseCommands() {
		if(analiseIf()) {	
			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				return analiseCommands();
			} else {
				return true;
			}
		} else if(analiseWhileStatement()) {
			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				return analiseCommands();
			} else {
				return true;
			}
		} else if(analiseReadStatement()) {
			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				return analiseCommands();
			} else {
				return true;
			}
		} else if(analiseAttribution()) {
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
				TokensFlow.next();
				
				if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
					return analiseCommands();
				} else {
					return true;
				}
			}
		} else if(analiseWriteStatement()) {
			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				return analiseCommands();
			} else {
				return true;
			}
		} else if(AnalyzerSecondary.analiseReturn()) {
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
				TokensFlow.next();
				return true;
			}
		}

		return false;
	}
	
	//<While Statement> ::= 'while''(' <Expression> ')' '{' <Commands> '}'
	public static void analiseWhileStatement() {
		Util.handleTerminal("while", true, false);
		Util.handleTerminal("(", true, false);
		analiseExpression();
		Util.handleTerminal(")", true, false);
		Util.handleTerminal("{", true, false);
		if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
			analiseCommands();
			Util.handleTerminal("}", true, false);
			return;
		} else {
			Util.handleTerminal("}", true, false);
			return;
		}
	}
	
	//<Read Statement>   ::= 'read''(' <Reading_1> ')' ';'
	public static void analiseReadStatement() {
		Util.handleTerminal("read", true, false);
		Util.handleTerminal("(", true, false);
		AnalyzerSecondary.analiseReading1();
		Util.handleTerminal(")", true, false);
		Util.handleTerminal(";", true, false);
	}
	
	
	//COMO IDENTIFICAR O ERRO AQUI??????
	// <Attribution> ::= <Increment>Identifier<Array Verification><Attr> | Identifier<Array Verification><Attr><Verif>
	public static boolean analiseAttribution() {
		if(AnalyzerSecondary.analiseIncrement()) {
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
				TokensFlow.next();
				
				if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
					if(AnalyzerSecondary.analiseArrayVerification()) {
						
						if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
							return AnalyzerSecondary.analiseAttr();
						} else {
							return true;
						}
						
					}
				} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					return AnalyzerSecondary.analiseAttr();
				} else {
					return true;
				}
			}
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) { //segunda regra
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				if(AnalyzerSecondary.analiseArrayVerification()) {
					
					if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
						if(AnalyzerSecondary.analiseAttr()) {
							return AnalyzerSecondary.analiseVerif();
						}
					} else {
						return AnalyzerSecondary.analiseVerif();
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
	
}