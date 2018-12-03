package controller;

import java.util.LinkedList;
import java.util.List;

import model.First;
import model.Token;
import model.TokensFlow;
import model.Util;

/**
 * Classe que implementa os principais métodos para realização da análise sintática.
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
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
		if(Util.handleTerminal("class", true, false)) {
			if(TokensFlow.hasNext()) {
				AnalyzerSecondary.analiseClassIdentification();
				
				while(TokensFlow.hasNext()) {
					if(First.check("MoreClasses", TokensFlow.getToken())) {
						break;
					}
					
					TokensFlow.next();
				}
				
				return;
			}
		} else {
			while(TokensFlow.hasNext()) {
				if(First.check("MoreClasses", TokensFlow.getToken())) {
					break;
				}
				
				TokensFlow.next();
			}
			
			return;
		}
	}

	//<Constant Declaration> ::= 'const' '{' <Constants> '}' | <> 
	public static void analiseConstantDeclaration() {	
		Util.handleTerminal("const", true, false);
		
		if(!Util.handleTerminal("{", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("Constants", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}")) {
					break;
				}
				
				if(First.check("ClassDeclaration", TokensFlow.getToken())) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Constants);
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Constants);
				Util.addError(list.toString());
				return;
			}
		}
		
		AnalyzerSecondary.analiseConstants();
		
		if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Constants))) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("ClassDeclaration", TokensFlow.getToken())) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Constants);
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Constants);
				Util.addError(list.toString());
				return;
			}
			
		}
	}
	
	//<Variable Declaration> ::= 'variables' '{' <Variable> '}' | <> 
	public static void analiseVariableDeclaration() {
		Util.handleTerminal("variables", true, false);
		
		if(!Util.handleTerminal("{", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("Variable", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}")) {
					break;
				}
				
				if(First.check("MethodDeclaration", TokensFlow.getToken())) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Variable);
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Variable);
				Util.addError(list.toString());
				return;
			}
		}

		AnalyzerSecondary.analiseVariable();

		if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Variable))) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("MethodDeclaration", TokensFlow.getToken())) {
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				return;
			}
		}

	}
	
	//<Method Declaration> ::= 'method' <Type> Identifier '(' <Parameter Declaration> ')' '{' <Variable Declaration> <Commands> '}' <More Methods>
	public static void analiseMethodDeclaration() {
		if(Util.handleTerminal("method", true, false)) {
			
			AnalyzerSecondary.analiseType();
			
			if(!Util.handleTerminal("IDENTIFICADOR", false, false)) {
				if(TokensFlow.isEmpty()) {
					return;
				}
				
				while(TokensFlow.hasNext()) {
					if(TokensFlow.getToken().getValue().equals("(") ||
						First.check("ParameterDeclaration", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals(")") ||
						TokensFlow.getToken().getValue().equals("{") ||
						First.check("VariableDeclaration", TokensFlow.getToken()) ||
						First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreMethods", TokensFlow.getToken())) {
						break;
					}
					
					if(First.check("MoreClasses", TokensFlow.getToken())) {
						LinkedList<String> list = new LinkedList<String>();
						list.add("(");
						Util.addError(list.toString());
						return;
					}
				}
				
				if(TokensFlow.isEmpty()) {
					LinkedList<String> list = new LinkedList<String>();
					list.add("(");
					Util.addError(list.toString());
					return;
				}

			}
			
			if(!Util.handleTerminal("(", true, false)) {
				if(TokensFlow.isEmpty()) {
					return;
				}
								
				while(TokensFlow.hasNext()) {
					if(First.check("ParameterDeclaration", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals(")") ||
							TokensFlow.getToken().getValue().equals("{") ||
							First.check("VariableDeclaration", TokensFlow.getToken()) ||
							First.check("Commands", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals("}") ||
							First.check("MoreMethods", TokensFlow.getToken())) {
							break;
					}
					
					if(First.check("MoreClasses", TokensFlow.getToken())) {
						LinkedList<String> list = new LinkedList<String>();
						list.addAll(First.ParameterDeclaration);
						list.add(")");
						Util.addError(list.toString());
						return;
					}
				}
				
				if(TokensFlow.isEmpty()) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.ParameterDeclaration);
					list.add(")");
					Util.addError(list.toString());
					return;
				}
				
			}
			
			if(TokensFlow.hasNext() && First.check("ParameterDeclaration", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseParameterDeclaration();

				if(!Util.handleTerminal(")", true, false)) {
					if(TokensFlow.isEmpty()) {
						return;
					}
					
					while(TokensFlow.hasNext()) {
						if(TokensFlow.getToken().getValue().equals("{") ||
								First.check("VariableDeclaration", TokensFlow.getToken()) ||
								First.check("Commands", TokensFlow.getToken()) ||
								TokensFlow.getToken().getValue().equals("}") ||
								First.check("MoreMethods", TokensFlow.getToken())) {
								break;
						}
						
						if(First.check("MoreClasses", TokensFlow.getToken())) {
							LinkedList<String> list = new LinkedList<String>();
							list.add("{");
							Util.addError(list.toString());
							return;
						}
						
						TokensFlow.next();
					}
					
					if(TokensFlow.isEmpty()) {
						LinkedList<String> list = new LinkedList<String>();
						list.add("{");
						Util.addError(list.toString());
						return;
					}
				}
				
				if(!Util.handleTerminal("{", true, false)) {
					if(TokensFlow.isEmpty()) {
						return;
					}
					
					while(TokensFlow.hasNext()) {
						if(First.check("VariableDeclaration", TokensFlow.getToken()) ||
								First.check("Commands", TokensFlow.getToken()) ||
								TokensFlow.getToken().getValue().equals("}") ||
								First.check("MoreMethods", TokensFlow.getToken())) {
								break;
						}
						
						if(First.check("MoreClasses", TokensFlow.getToken())) {
							LinkedList<String> list = new LinkedList<String>();
							list.addAll(First.VariableDeclaration);
							list.addAll(First.Commands);
							list.add("}");
							Util.addError(list.toString());
							return;
						}
						
						TokensFlow.next();
					}
					
					if(TokensFlow.isEmpty()) {
						LinkedList<String> list = new LinkedList<String>();
						list.addAll(First.VariableDeclaration);
						list.addAll(First.Commands);
						list.add("}");
						Util.addError(list.toString());
						return;
					}
				}

				if(TokensFlow.hasNext() && First.check("VariableDeclaration", TokensFlow.getToken())) {

					analiseVariableDeclaration();
					
					if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
						analiseCommands();
						
						if(!Util.handleTerminal("}", true, false)) {
							if(TokensFlow.isEmpty()) {
								return;
							}
							
							while(TokensFlow.hasNext()) {
								if(First.check("MoreMethods", TokensFlow.getToken())) {
									break;
								}
								
								if(First.check("MoreClasses", TokensFlow.getToken())) {
									return;
								}
								
								TokensFlow.next();
							}
							
							if(TokensFlow.isEmpty()) {
								LinkedList<String> list = new LinkedList<String>();
								list.add("}");
								Util.addError(list.toString());
								return;
							}
						}
						
						if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
							AnalyzerSecondary.analiseMoreMethods();
							return;
						} else {
							return;
						}
					
					} else {
						if(!Util.handleTerminal("}", true, false)) {
							if(TokensFlow.isEmpty()) {
								return;
							}
							
							while(TokensFlow.hasNext()) {
								if(First.check("MoreMethods", TokensFlow.getToken())) {
									break;
								}
								
								if(First.check("MoreClasses", TokensFlow.getToken())) {
									return;
								}
								TokensFlow.next();
							}
							
							if(TokensFlow.isEmpty()) {
								LinkedList<String> list = new LinkedList<String>();
								list.add("}");
								Util.addError(list.toString());
								return;
							}
						}
						
						if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
							AnalyzerSecondary.analiseMoreMethods();
							return;
						} else {
							return;
						}
					}
				} else if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
					analiseCommands();
					if(!Util.handleTerminal("}", true, false)) {
						if(TokensFlow.isEmpty()) {
							return;
						}
						
						while(TokensFlow.hasNext()) {
							if(First.check("MoreMethods", TokensFlow.getToken())) {
								break;
							}
							
							if(First.check("MoreClasses", TokensFlow.getToken())) {
								return;
							}
							
							TokensFlow.next();
						}
						
						if(TokensFlow.isEmpty()) {
							LinkedList<String> list = new LinkedList<String>();
							list.add("}");
							Util.addError(list.toString());
							return;
						}
					}
					if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
						AnalyzerSecondary.analiseMoreMethods();
						return;
					} else {
						return;
					}
				} else {
					if(!Util.handleTerminal("}", true, false)) {
						if(TokensFlow.isEmpty()) {
							return;
						}
						
						while(TokensFlow.hasNext()) {
							if(First.check("MoreMethods", TokensFlow.getToken())) {
								break;
							}
							
							if(First.check("MoreClasses", TokensFlow.getToken())) {
								return;
							}
							
							TokensFlow.next();
						}
						
						if(TokensFlow.isEmpty()) {
							LinkedList<String> list = new LinkedList<String>();
							list.add("}");
							Util.addError(list.toString());
							return;
						}
					}
					
					if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
						AnalyzerSecondary.analiseMoreMethods();
						return;
					} else {
						return;
					}
				}
			} else {
				if(!Util.handleTerminal(")", true, false)) {
					if(TokensFlow.isEmpty()) {
						return;
					}
					
					while(TokensFlow.hasNext()) {
						if(TokensFlow.getToken().getValue().equals("{") ||
								First.check("VariableDeclaration", TokensFlow.getToken()) ||
								First.check("Commands", TokensFlow.getToken()) ||
								TokensFlow.getToken().getValue().equals("}") ||
								First.check("MoreMethods", TokensFlow.getToken())) {
								break;
						}
						
						if(First.check("MoreClasses", TokensFlow.getToken())) {
							LinkedList<String> list = new LinkedList<String>();
							list.add("{");
							Util.addError(list.toString());
							return;
						}
						
						TokensFlow.next();
					}
					
					if(TokensFlow.isEmpty()) {
						LinkedList<String> list = new LinkedList<String>();
						list.add("{");
						Util.addError(list.toString());
						return;
					}
				}
				
				if(!Util.handleTerminal("{", true, false)) {
					if(TokensFlow.isEmpty()) {
						return;
					}
					
					while(TokensFlow.hasNext()) {
						if(First.check("VariableDeclaration", TokensFlow.getToken()) ||
								First.check("Commands", TokensFlow.getToken()) ||
								TokensFlow.getToken().getValue().equals("}") ||
								First.check("MoreMethods", TokensFlow.getToken())) {
								break;
						}
						
						if(First.check("MoreClasses", TokensFlow.getToken())) {
							LinkedList<String> list = new LinkedList<String>();
							list.addAll(First.VariableDeclaration);
							list.addAll(First.Commands);
							list.add("}");
							Util.addError(list.toString());
							return;
						}
						
						TokensFlow.next();
					}
					
					if(TokensFlow.isEmpty()) {
						LinkedList<String> list = new LinkedList<String>();
						list.addAll(First.VariableDeclaration);
						list.addAll(First.Commands);
						list.add("}");
						Util.addError(list.toString());
						return;
					}
				}

				if(TokensFlow.hasNext() && First.check("VariableDeclaration", TokensFlow.getToken())) { 
					analiseVariableDeclaration();
					if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
						analiseCommands();

						if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
							if(TokensFlow.isEmpty()) {
								return;
							}
							
							while(TokensFlow.hasNext()) {
								if(First.check("MoreMethods", TokensFlow.getToken())) {
									break;
								}
								
								if(First.check("MoreClasses", TokensFlow.getToken())) {
									return;
								}
								
								TokensFlow.next();
							}
							
							if(TokensFlow.isEmpty()) {
								LinkedList<String> list = new LinkedList<String>();
								list.add("}");
								Util.addError(list.toString());
								return;
							}
						}
						
						if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
							AnalyzerSecondary.analiseMoreMethods();
							return;
						} else {
							return;
						}
						
					} else {
						if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
							if(TokensFlow.isEmpty()) {
								return;
							}
							
							while(TokensFlow.hasNext()) {
								if(First.check("MoreMethods", TokensFlow.getToken()) ||
										First.check("Commands", TokensFlow.getToken())) {
									break;
								}
								
								if(First.check("MoreClasses", TokensFlow.getToken())) {
									return;
								}
								
								TokensFlow.next();
							}
							
							if(TokensFlow.isEmpty()) {
								LinkedList<String> list = new LinkedList<String>();
								list.addAll(First.Commands);
								list.add("}");
								Util.addError(list.toString());
								return;
							}
						}
						
						if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
							analiseCommands();
							if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
								if(TokensFlow.isEmpty()) {
									return;
								}
								
								while(TokensFlow.hasNext()) {
									if(First.check("MoreMethods", TokensFlow.getToken())) {
										break;
									}
									
									if(First.check("MoreClasses", TokensFlow.getToken())) {
										return;
									}
									
									TokensFlow.next();
								}
								
								if(TokensFlow.isEmpty()) {
									LinkedList<String> list = new LinkedList<String>();
									list.add("}");
									Util.addError(list.toString());
									return;
								}
							}
							if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
								AnalyzerSecondary.analiseMoreMethods();
								return;
							} else {
								return;
							}
						}
						
						if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
							AnalyzerSecondary.analiseMoreMethods();
							return;
						} else {
							return;
						}
					}
					
				} else if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
					analiseCommands();
					if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
						if(TokensFlow.isEmpty()) {
							return;
						}
						
						while(TokensFlow.hasNext()) {
							if(First.check("MoreMethods", TokensFlow.getToken())) {
								break;
							}
							
							if(First.check("MoreClasses", TokensFlow.getToken())) {
								return;
							}
							
							TokensFlow.next();
						}
						
						if(TokensFlow.isEmpty()) {
							LinkedList<String> list = new LinkedList<String>();
							list.add("}");
							Util.addError(list.toString());
							return;
						}
					}
					if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
						AnalyzerSecondary.analiseMoreMethods();
						return;
					} else {
						return;
					}
				} else {
					
					if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
						if(TokensFlow.isEmpty()) {
							return;
						}
						
						while(TokensFlow.hasNext()) {
							if(First.check("MoreMethods", TokensFlow.getToken()) ||
									First.check("Commands", TokensFlow.getToken())) {
								break;
							}
							
							if(First.check("MoreClasses", TokensFlow.getToken())) {
								return;
							}
							
							TokensFlow.next();
						}
						
						if(TokensFlow.isEmpty()) {
							LinkedList<String> list = new LinkedList<String>();
							list.addAll(First.Commands);
							list.add("}");
							Util.addError(list.toString());
							return;
						}
					}
					
					if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
						analiseCommands();
						if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
							if(TokensFlow.isEmpty()) {
								return;
							}
							
							while(TokensFlow.hasNext()) {
								if(First.check("MoreMethods", TokensFlow.getToken())) {
									break;
								}
								
								if(First.check("MoreClasses", TokensFlow.getToken())) {
									return;
								}
								
								TokensFlow.next();
							}
							
							if(TokensFlow.isEmpty()) {
								LinkedList<String> list = new LinkedList<String>();
								list.add("}");
								Util.addError(list.toString());
								return;
							}
						}
						if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
							AnalyzerSecondary.analiseMoreMethods();
							return;
						} else {
							return;
						}
					}
					
					
					if(TokensFlow.hasNext() && First.check("MoreMethods", TokensFlow.getToken())) {
						AnalyzerSecondary.analiseMoreMethods();
						return;
					} else {
						return;
					}
				}
			}
		} else {

			while(TokensFlow.hasNext()) {
				if(First.check("MoreMethods", TokensFlow.getToken())) {
					break;
				}
				
				TokensFlow.next();
			}
			
			return;
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

		if(!Util.handleTerminal("(", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(TokensFlow.getToken().getValue().equals(")") ||
						First.check("Writing1", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals(";")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) || 
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Writing1);
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Writing1);
				Util.addError(list.toString());
				return;
			}
		}

		AnalyzerSecondary.analiseWriting1();
		
		if(!Util.handleTerminal(")", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(TokensFlow.getToken().getValue().equals(";")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) || 
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.add(";");
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.add(";");
				Util.addError(list.toString());
				return;
			}
		}
		
		
		if(!Util.handleTerminal(";", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("Commands", TokensFlow.getToken()) || 
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				return;
			}
		}
	}
	
	//<If Statement> ::= 'if''('<Expression>')' 'then' '{'<Commands>'}'<Else Statement>
	public static void analiseIf() {
		Util.handleTerminal("if", true, false);
		
		if(!Util.handleTerminal("(", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(TokensFlow.getToken().getValue().equals("(") ||
						First.check("Expression", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals(")") ||
						TokensFlow.getToken().getValue().equals("then") ||
						TokensFlow.getToken().getValue().equals("{") ||
						First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						TokensFlow.getToken().getValue().equals("ElseStatement")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) || 
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Expression);
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Expression);
				Util.addError(list.toString());
				return;
			}
		}
		
		
		analiseExpression();
		
		if(!Util.handleTerminal(")", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(TokensFlow.getToken().getValue().equals("then") ||
						TokensFlow.getToken().getValue().equals("{") ||
						First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						TokensFlow.getToken().getValue().equals("ElseStatement")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) || 
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.add("then");
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.add("then");
				Util.addError(list.toString());
				return;
			}
		}
		
		if(!Util.handleTerminal("then", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(TokensFlow.getToken().getValue().equals("{") ||
						First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						TokensFlow.getToken().getValue().equals("ElseStatement")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) || 
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.add("{");
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.add("{");
				Util.addError(list.toString());
				return;
			}
		}
		
		if(!Util.handleTerminal("{", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						TokensFlow.getToken().getValue().equals("ElseStatement")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) || 
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Commands);
					list.add("}");
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Commands);
				list.add("}");
				Util.addError(list.toString());
				return;
			}
		}
		

		if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
			analiseCommands();
			
			if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
				if(TokensFlow.isEmpty()) {
					return;
				}
				
				while(TokensFlow.hasNext()) {
					if(TokensFlow.getToken().getValue().equals("ElseStatement")) {
						break;
					}
					
					if(TokensFlow.getToken().getValue().equals("}") ||
							First.check("MoreClasses", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals("method")) {
						return;
					}
					
					TokensFlow.next();
				}
				
				if(TokensFlow.isEmpty()) {
					return;
				}
			}
			
			
			if(TokensFlow.hasNext() && First.check("ElseStatement", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseElseStatement();
				return;
			} else {
				return;
			}

		} else {
			if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
				if(TokensFlow.isEmpty()) {
					return;
				}
				
				while(TokensFlow.hasNext()) {
					if(TokensFlow.getToken().getValue().equals("ElseStatement") ||
							First.check("Commands", TokensFlow.getToken())) {
						break;
					}
					
					if(First.check("Commands", TokensFlow.getToken()) || 
							TokensFlow.getToken().getValue().equals("}") ||
							First.check("MoreClasses", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals("method")) {
						return;
					}
					
					TokensFlow.next();
				}
				
				if(TokensFlow.isEmpty()) {
					return;
				}
			}
			
			if(First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				
				if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
					if(TokensFlow.isEmpty()) {
						return;
					}
					
					while(TokensFlow.hasNext()) {
						if(TokensFlow.getToken().getValue().equals("ElseStatement")) {
							break;
						}
						
						if(TokensFlow.getToken().getValue().equals("}") ||
								First.check("MoreClasses", TokensFlow.getToken()) ||
								TokensFlow.getToken().getValue().equals("method")) {
							return;
						}
						
						TokensFlow.next();
					}
					
					if(TokensFlow.isEmpty()) {
						return;
					}
				}
				
				
				if(TokensFlow.hasNext() && First.check("ElseStatement", TokensFlow.getToken())) {
					AnalyzerSecondary.analiseElseStatement();
					return;
				} else {
					return;
				}
			}
			
			if(TokensFlow.hasNext() && First.check("ElseStatement", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseElseStatement();
				return;
			} else {
				return;
			}
		}
	}
	
	//<Commands>::= <If Statement><Commands> | <> | <While Statement><Commands> | <Read Statement><Commands>
    //              | <Attribution>';'<Commands> | <Write Statement><Commands> | <Return>';'
	public static void analiseCommands() {
		if(TokensFlow.hasNext() && First.check("If", TokensFlow.getToken())) {
			analiseIf();
			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				return;
			} else {
				return;
			}
		} else if(TokensFlow.hasNext() && First.check("While", TokensFlow.getToken())) {
			analiseWhileStatement();
			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				return;
			} else {
				return;
			}
		} else if(TokensFlow.hasNext() && First.check("Read", TokensFlow.getToken())) {
			analiseReadStatement();
			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				return;
			} else {
				return;
			}
		} else if(TokensFlow.hasNext() && First.check("Attribution", TokensFlow.getToken())) {
			analiseAttribution();

			Util.handleTerminal(";", true, false);

			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				return;
			} else {
				return;
			}
		} else if(TokensFlow.hasNext() && First.check("Write", TokensFlow.getToken())) {
			analiseWriteStatement();
			if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				return;
			} else {
				return;
			}
		} else if(TokensFlow.hasNext() && First.check("Return", TokensFlow.getToken())) {
			AnalyzerSecondary.analiseReturn();
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
				TokensFlow.next();
				return;
			}
		} 
		
		Util.addError(First.Commands.toString());
		
		while(TokensFlow.hasNext()) {
			if(TokensFlow.getToken().getValue().equals("}") || First.check("Commands", TokensFlow.getToken())) {
				break;
			}
		}
		
	}
	
	//<While Statement> ::= 'while''(' <Expression> ')' '{' <Commands> '}'
	public static void analiseWhileStatement() {
		Util.handleTerminal("while", true, false);
		
		if(!Util.handleTerminal("(", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("Expression", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals(")") ||
						TokensFlow.getToken().getValue().equals("{") ||
						First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Expression);
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Expression);
				Util.addError(list.toString());
				return;
			}
		}
		
		analiseExpression();
		
		if(!Util.handleTerminal(")", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(TokensFlow.getToken().getValue().equals("{") ||
						First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.add("{");
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.add("{");
				Util.addError(list.toString());
				return;
			}
		}
		
		if(!Util.handleTerminal("{", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Commands);
					list.add("}");
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Commands);
				list.add("}");
				Util.addError(list.toString());
				return;
			}
		}
		
				
		if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) {
			analiseCommands();
			
			if(!Util.handleTerminal("}", true, false)) {
				if(TokensFlow.isEmpty()) {
					return;
				}
				
				while(TokensFlow.hasNext()) {
					if(First.check("Commands", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals("}") ||
							First.check("MoreClasses", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals("method")) {
						return;
					}
					
					TokensFlow.next();
				}
				
				if(TokensFlow.isEmpty()) {
					return;
				}
			}

			return;
		} else {
			if(!Util.handleTerminal("}", true, false, new LinkedList<String>(First.Commands))) {
				if(TokensFlow.isEmpty()) {
					return;
				}
				
				while(TokensFlow.hasNext()) {
					if(First.check("Commands", TokensFlow.getToken())) {
						break;
					}
					
					if(TokensFlow.getToken().getValue().equals("}") ||
							First.check("MoreClasses", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals("method")) {
						return;
					}
					
					TokensFlow.next();
				}
				
				if(TokensFlow.isEmpty()) {
					return;
				}
			}
			
			if(First.check("Commands", TokensFlow.getToken())) {
				analiseCommands();
				
				if(!Util.handleTerminal("}", true, false)) {
					if(TokensFlow.isEmpty()) {
						return;
					}
					
					while(TokensFlow.hasNext()) {
						if(First.check("Commands", TokensFlow.getToken()) ||
								TokensFlow.getToken().getValue().equals("}") ||
								First.check("MoreClasses", TokensFlow.getToken()) ||
								TokensFlow.getToken().getValue().equals("method")) {
							return;
						}
						
						TokensFlow.next();
					}
					
					if(TokensFlow.isEmpty()) {
						return;
					}
				}
				

				return;
			}
			
			return;
		}
	}
	
	//<Read Statement>   ::= 'read''(' <Reading_1> ')' ';'
	public static void analiseReadStatement() {
		Util.handleTerminal("read", true, false);
		
		if(!Util.handleTerminal("(", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("Reading1", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals(")") ||
						TokensFlow.getToken().getValue().equals(";")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.addAll(First.Reading1);
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.addAll(First.Reading1);
				Util.addError(list.toString());
				return;
			}
		}
		
		AnalyzerSecondary.analiseReading1();
		
		if(!Util.handleTerminal(")", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(TokensFlow.getToken().getValue().equals(";")) {
					break;
				}
				
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					LinkedList<String> list = new LinkedList<String>();
					list.add(";");
					Util.addError(list.toString());
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				LinkedList<String> list = new LinkedList<String>();
				list.add(";");
				Util.addError(list.toString());
				return;
			}
		}
		
		if(!Util.handleTerminal(";", true, false)) {
			if(TokensFlow.isEmpty()) {
				return;
			}
			
			while(TokensFlow.hasNext()) {
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					return;
				}
				
				TokensFlow.next();
			}
			
			if(TokensFlow.isEmpty()) {
				return;
			}
		}
	}
	
	// <Attribution> ::= <Increment>Identifier<Array Verification><Attr> 
	//					| Identifier<Array Verification><Attr><Verif>
	public static void analiseAttribution() {
		if(TokensFlow.hasNext() && First.check("Increment", TokensFlow.getToken())) {
			AnalyzerSecondary.analiseIncrement();
			
			if(!Util.handleTerminal("IDENTIFICADOR", false, false)) {
				if(TokensFlow.isEmpty()) {
					return;
				}
				
				while(TokensFlow.hasNext()) {
					if(First.check("Commands", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals("}") ||
							TokensFlow.getToken().getValue().equals(";") ||
							First.check("MoreClasses", TokensFlow.getToken()) ||
							TokensFlow.getToken().getValue().equals("method")) {
						return;
					}
					
					TokensFlow.next();
				}
				
				if(TokensFlow.isEmpty()) {
					return;
				}
			}
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseArrayVerification();
				if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					AnalyzerSecondary.analiseAttr();
					return;
				} else {
					return;
				}
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseAttr();
				return;
			} else {
				return;
			}
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) { 
			TokensFlow.next();	
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseArrayVerification();
				if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					AnalyzerSecondary.analiseAttr();
					AnalyzerSecondary.analiseVerif();
					return;
				} else {
					AnalyzerSecondary.analiseVerif();
					return;
				}
				
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				AnalyzerSecondary.analiseAttr();
				AnalyzerSecondary.analiseVerif();
				return;
			} else {
				AnalyzerSecondary.analiseVerif();
				return;
			}
		} else {
			Util.addError(First.Attribution.toString());

			while(TokensFlow.hasNext()) {
				if(First.check("Commands", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("}") ||
						TokensFlow.getToken().getValue().equals(";") ||
						First.check("MoreClasses", TokensFlow.getToken()) ||
						TokensFlow.getToken().getValue().equals("method")) {
					return;
				}
				
				TokensFlow.next();
			}
		}
		
	}
	
}