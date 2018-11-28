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
	public static boolean analiseExpression() { 
		if(AnalyzerSecondary.analiseAddExp()) {
			return AnalyzerSecondary.analiseRelationalExp();
		}
		
		return false;
	}

	//<Write Statement> ::= 'write''('<Writing_1>')' ';'
	public static boolean analiseWriteStatement() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("write")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(") ) {
				TokensFlow.next();	
				
				if(AnalyzerSecondary.analiseWriting1()) {
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
	
	//<If Statement> ::= 'if''('<Expression>')' 'then' '{'<Commands>'}'<Else Statement>
	public static boolean analiseIf() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("if")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
				TokensFlow.next();
				
				if(analiseExpression()) {
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {

						TokensFlow.next();
						
						if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("then")) {
							TokensFlow.next();

							if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
								TokensFlow.next();
								if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {

									if(analiseCommands()) {
										if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
											TokensFlow.next();
											
											if(TokensFlow.hasNext() && First.check("ElseStatement", TokensFlow.getToken())) {
												return AnalyzerSecondary.analiseElseStatement();
											} else {
												return true;
											}
										}
									}
								} else {
									if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
										TokensFlow.next();
										
										if(TokensFlow.hasNext() && First.check("ElseStatement", TokensFlow.getToken())) {
											return AnalyzerSecondary.analiseElseStatement();
										} else {
											return true;
										}
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
	public static boolean analiseWhileStatement() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("while")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
				TokensFlow.next();
				
				if(analiseExpression()) {
					
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
						TokensFlow.next();
						
						if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
							TokensFlow.next();
							
							if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
								if(analiseCommands()) {
									if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
										TokensFlow.next();
										return true;
									}
								}
							} else {
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
		
		return false;
	}
	
	//<Read Statement>   ::= 'read''(' <Reading_1> ')' ';'
	public static boolean analiseReadStatement() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("read")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
				TokensFlow.next();
				
				if(AnalyzerSecondary.analiseReading1()) {

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