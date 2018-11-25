package controller;

import java.util.LinkedList;

import model.First;
import model.Token;
import model.TokensFlow;
import model.Util;

public class AnalyzerSecondary {
	
	//<More Classes> ::= <Class Declaration><More Classes> | <>
	public static boolean analiseMoreClasses() {
		if(Analyzer.analiseClassDeclaration()) {
			
			if(TokensFlow.hasNext() && First.check("MoreClasses", TokensFlow.getToken())) {
				return analiseMoreClasses();
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	//<Class Identification> ::= Identifier <Class Heritage> '{' <Class Body> '}'
	public static boolean analiseClassIdentification() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ClassHeritage", TokensFlow.getToken())) {
				if(analiseClassHeritage()) {
					
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
						TokensFlow.next();
						
						if(analiseClassBody()) {
							if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
								TokensFlow.next();
								return true;
							}
						}
						
						
					}
				}
 			} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
				TokensFlow.next();
				if(analiseClassBody()) {
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
						TokensFlow.next();
						return true;
					}
				}
			}
			
		}
		
		return false;
	}
	
	//<Class Heritage> ::= 'extends' Identifier | <>
	public static boolean analiseClassHeritage() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("extends")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
				TokensFlow.next();
				return true;
			}
		}
		
		return false;
	}
	
	//<Class Body> ::= <Class Attributes> <Class Methods>
	public static boolean analiseClassBody() {
		if(analiseClassAttributes()) {
			if(TokensFlow.hasNext() && First.check("ClassMethods", TokensFlow.getToken())) {
				return analiseClassMethods();
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	//<Class Attributes> ::= <Variable Declaration> 
	public static boolean analiseClassAttributes() {
		if(TokensFlow.hasNext() && First.check("VariableDeclaration", TokensFlow.getToken())) {
			return Analyzer.analiseVariableDeclaration();
		} else {
			return true;
		}
	}
	
	//<Class Methods> ::= <Method Declaration> | <>
	public static boolean analiseClassMethods() {

		return Analyzer.analiseMethodDeclaration();
	}
	
	
	
	
	
	
	
	
	
	
	//<Constants> ::= Type <ConstAttribution> <More Constants> 
	public static boolean analiseConstants() {
		if(TokensFlow.hasNext() && Util.isType(TokensFlow.getToken())) {
			TokensFlow.next();
			if(analiseConstAttribution()) {
				if(analiseMoreConstants()) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	//<More Constants> ::= ',' <ConstAttribution> <More Constants> | ';' <New Declaration> 
	public static boolean analiseMoreConstants() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
						
			if(analiseConstAttribution()) {

				return analiseMoreConstants();
			}
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("NewDeclaration", TokensFlow.getToken())) {
				return analiseNewDeclaration();
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	//<New Declaration> ::= <Constants> | <>
	public static boolean analiseNewDeclaration() {
		return analiseConstants();
	}

	
	//<ConstAttribution> ::= Identifier '=' <Value>
	public static boolean analiseConstAttribution() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("=")) {
				TokensFlow.next();
				return analiseValue();
			}
		}
		
		return false;
	}
	
	//<Value> ::= Number | 'true' | 'false' | CadeCharacters
	public static boolean analiseValue() {
		if(TokensFlow.getToken().getTokenClass().equals("NUMERO") 
				|| TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES") 
				|| TokensFlow.getToken().getValue().equals("true")
				|| TokensFlow.getToken().getValue().equals("false")
				) {
			
			TokensFlow.next();
			return true;
		}
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	
	//<Variable> ::= Type <Variable2> | Identifier <Variable2>
	public static boolean analiseVariable() {
		if(TokensFlow.hasNext() && Util.isType(TokensFlow.getToken())) {
			TokensFlow.next();

			return analiseVariable2();
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			return analiseVariable2();
		}
		
		return false;
	}
	
	//<Variable2> ::= <Name> <More Variables>
	public static boolean analiseVariable2() {

		if(analiseName()) {

			if(TokensFlow.hasNext() && First.check("MoreVariables", TokensFlow.getToken())) {
				return analiseMoreVariables();
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	//<More Variables> ::= <Variable> | <>
	public static boolean analiseMoreVariables() {
		return analiseVariable();
	}
	
	//<Name> ::= Identifier<Array Verification><More Names>
	public static boolean analiseName() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {

				if(analiseArrayVerification()) {
					return analiseMoreNames();
				}

			} else {
				return analiseMoreNames();
			}
		}
		
		return false;
	}
	
	//<More Names> ::= ',' <Name> | ';'
	public static boolean analiseMoreNames() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			
			return analiseName();
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
			TokensFlow.next();
			return true;
		}
		
		return false;
	}
	
	
	
	
	
	
	
	
	
	
	//<More Methods> ::= <Method Declaration> | <>      
	public static boolean analiseMoreMethods() {
		return Analyzer.analiseMethodDeclaration();
	}
	
	//<Parameter Declaration> ::= <Parameter Declaration2> | <>
	public static boolean analiseParameterDeclaration() {
		return analiseParameterDeclaration2();
	}
	
	//<Parameter Declaration2> ::= <Type> Identifier <Array Verification> <More Parameters>
	public static boolean analiseParameterDeclaration2() {
		if(TokensFlow.hasNext() && analiseType()) {
			
			if(TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
				TokensFlow.next();
				
				if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
					if(analiseArrayVerification()) {
						
						if(TokensFlow.hasNext() && First.check("MoreParameters", TokensFlow.getToken())) {
							return analiseMoreParameters();
						} else {
							return true;
						}
						
					}
				} else {
					if(TokensFlow.hasNext() && First.check("MoreParameters", TokensFlow.getToken())) {
						return analiseMoreParameters();
					} else {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	//<Type> ::= Type | Identifier 
	public static boolean analiseType() {
		if(TokensFlow.hasNext() && Util.isType(TokensFlow.getToken())) {
			TokensFlow.next();
			return true;
		} else if(TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			return true;
		}
				
		return false;
	}
	
	//<More Parameters> ::= ',' <Parameter Declaration2> | <>
	public static boolean analiseMoreParameters() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			return analiseParameterDeclaration2();
		}
		
		return false;
	}
	
	//<Array Verification> ::= '['<Array Index>']'<DoubleArray> | <>
	public static boolean analiseArrayVerification() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("[")) {
			TokensFlow.next();
			
			if(analiseArrayIndex()) {
				if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("]")) {
					TokensFlow.next();
					
					if(TokensFlow.hasNext() && First.check("DoubleArray", TokensFlow.getToken())) {
						return analiseDoubleArray();
					} else {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	//<DoubleArray> ::= '['<Array Index>']' | <>
	public static boolean analiseDoubleArray() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("[")) {
			TokensFlow.next();
			
			if(analiseArrayIndex()) {
				if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("]")) {
					TokensFlow.next();
					return true;
				}
			}
		}
		
		return false;
	}
	
	//<Array Index> ::= <Add Exp>
	public static boolean analiseArrayIndex() {
		return analiseAddExp();
	}
	
	//<Return> ::= 'return' <Return1> 
	public static boolean analiseReturn() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("return")) {
			TokensFlow.next();
			
			return analiseReturn1();
		}
		
		return false;
	}
	
	//<Return1> ::= Identifier <Array Verification> | <Value>
	public static boolean analiseReturn1() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				return analiseArrayVerification();
			} else {
				return true;
			}
		} 
		
		return analiseValue();
	}
	

	
	
	
	//<Else Statement> ::= 'else''{'<Commands>'}' | <>
	public static boolean analiseElseStatement() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("else")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
				TokensFlow.next();

				if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) { 
					
					if(Analyzer.analiseCommands()) {
						if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
							TokensFlow.next();
							return true;
						}
					}
					
				} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
					TokensFlow.next();
					return true;
				}
			} 
		}
		
		return false;
	}
	
	
	
	
	
	
	
	//<Verif> ::= <Normal Attribution2> | <Complement>
	public static boolean analiseVerif() {
		if(analiseNormalAttribution2()) {
			return true;
		} else {
			return analiseComplement();
		}
	}
	
	//<Increment> ::= '++' | '--'
	public static boolean analiseIncrement() {
		if(TokensFlow.hasNext()) {
			if(TokensFlow.getToken().getValue() == "++" || TokensFlow.getToken().getValue() == "--") {
				TokensFlow.next();
				return true;
			} 
		}
		
		return false;
	}
	
	//<Normal Attribution2> ::= '=' <Normal Attribution3> | <Increment>  
	public static boolean analiseNormalAttribution2() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("=")) {
			TokensFlow.next();
			
			return analiseNormalAttribution3();
		} else {
			return analiseIncrement();
		}
	}
	
	//<Normal Attribution3> ::= <Expression> | CadeCaracters
	public static boolean analiseNormalAttribution3() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();
			return true;
		} else {
			return Analyzer.analiseExpression();
		}
	}

	
	
	
	
	
	
	
	//<Relational Exp> ::= RelationalOperator <Add Exp> <Logical Exp> | <Logical Exp>
	public static boolean analiseRelationalExp() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("OPERADOR_RELACIONAL")) {
			TokensFlow.next();
			
			if(analiseAddExp()) {
				if(TokensFlow.hasNext() && First.check("LogicalExp", TokensFlow.getToken())) {
					return analiseLogicalExp();
				} else {
					return true;
				}
			}
			
		} else {
			if(TokensFlow.hasNext() && First.check("LogicalExp", TokensFlow.getToken())) {
				return analiseLogicalExp();
			} else {
				return true;
			}
		}
			
		return false;
	}
	
	//<Logical Exp> ::= '||' <Expression> | '&&' <Expression> | <> 
	public static boolean analiseLogicalExp() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("||")) { 
			TokensFlow.next();
			return Analyzer.analiseExpression();
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("&&")) { 
			TokensFlow.next();
			return Analyzer.analiseExpression();
		}

		return false;
	}
	
	
	//<Add Exp> ::= <Mult Exp> <D>
	public static boolean analiseAddExp() { 
		if(analiseMultExp()) {
			if(TokensFlow.hasNext() && First.check("D", TokensFlow.getToken())) {
				return analiseD();
			} else {
				return true;
			}
		}	
		
		return false;
	}
	

	//<D> ::= '+' <Add Exp> | '-' <Add Exp> | <>
	public static boolean analiseD() { 
		if(TokensFlow.hasNext() && (TokensFlow.getToken().getValue().equals("+") || TokensFlow.getToken().getValue().equals("-"))) {
			TokensFlow.next();
			return analiseAddExp();
		}
		
		return false;
	}
	
	// <Mult Exp> ::= <Neg Exp> <E>
	public static boolean analiseMultExp() { 
		if(analiseNegExp()) {
			if(TokensFlow.hasNext() && First.check("E", TokensFlow.getToken())) {
				return analiseE();
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	//<E> ::= '*' <Mult Exp> | '/' <Mult Exp> | <>
	public static boolean analiseE() { 
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("*")) {
			TokensFlow.next();
			return analiseMultExp();
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("/")) {
			TokensFlow.next();
			return analiseMultExp();
		}
		
		return false;
	}
	
	////<Neg Exp>  ::= '-' <F> |  <Exp Value> <G> | '!' <H><Exp Value> | '++' <Exp Value> | '--'<Exp Value>
	//<Neg Exp>  ::= '-' <Exp Value> |  <Exp Value> <G> | '!' <Exp Value> | '++' <Exp Value> | '--'<Exp Value>
	public static boolean analiseNegExp() { 
		//inicio <Neg Exp>
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("-")) { //'-' <F>
			TokensFlow.next();
			return analiseExpValue();
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("!")) {
			TokensFlow.next();
			
			return analiseExpValue();
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("++")) {
			TokensFlow.next();
			return analiseExpValue();
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("--")) {
			TokensFlow.next();
			return analiseExpValue();
		} else {
			
			if(analiseExpValue()) {
				if(TokensFlow.hasNext() && First.check("G", TokensFlow.getToken())) {
					return analiseG();
				} else {
					return true;
				}
			}
		}
		
		return false;
	}
	

	//<G> ::= '--' | '++' | <>
	public static boolean analiseG() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("--")) {
			TokensFlow.next();
			return true;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("++")) {
			TokensFlow.next();
			return true;
		}
		
		return false;
	}
	
	////<Exp Value> ::= Number |  '(' <Expression> ')' |  Identifier<Array Verification><Attr><Param2> | 'true' | 'false' 
	public static boolean analiseExpValue() { 
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("NUMERO")) {
			TokensFlow.next();
			return true;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
			TokensFlow.next();
			
			if(Analyzer.analiseExpression()) {
				if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
					return true;
				}
			}
			
		} else if(TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) { 
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				
				if(analiseArrayVerification()) {
					
					if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
						
						if(analiseAttr()) {
							
							if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
								return analiseParam2();
							} else {
								return true;
							}
							
						} else {
							return false;
						}
						
					} else if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
						return analiseParam2();
					} else {
						return true;
					}
					
				} else {
					return false;
				}
				
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				
				if(analiseAttr()) {
					
					if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
						return analiseParam2();
					} else {
						return true;
					}
					
				} else {
					return false;
				}
				
			} else if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
				return analiseParam2();
			} else {
				return true;
			}
			
		} else if(TokensFlow.hasNext() && (TokensFlow.getToken().getValue().equals("true") || TokensFlow.getToken().getValue().equals("false"))) {
			TokensFlow.next();
			return true;
		}

		return false;
	}
	
	
	
	
	
	
	
	//<Param2>::= <Complement> | <>
	public static boolean analiseParam2() {
		return analiseComplement();
	}
	
	//<Complement> ::= '('<Param>')'
	public static boolean analiseComplement() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
			TokensFlow.next();

			if(TokensFlow.hasNext() && First.check("Param", TokensFlow.getToken())) {
				
				if(analiseParam()) {
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
						TokensFlow.next();
						return true;
					}
				} else {
					return false;
				}
				
			} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
				TokensFlow.next();
				return true;
			}
		} 
		
		return false;
	}
	
	//<Param> ::= <> | <Expression><MoreParam> | CadeCaracters<MoreParam> 
	public static boolean analiseParam() {
		
		if(Analyzer.analiseExpression()) {
			
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				return analiseMoreParam();
			} else {
				return true;
			}

		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				return analiseMoreParam();
			} else {
				return true;
			}
			
		}
		
		return false;
	}
	
	//<MoreParam> ::= ','<ObrigatoryParam> | <>
	public static boolean analiseMoreParam() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			return analiseObrigatoryParam();
		}
		
		return false;
	}
	
	//<ObrigatoryParam> ::= <Expression><MoreParam> | CadeCaracters<MoreParam>
	public static boolean analiseObrigatoryParam() {
		if(Analyzer.analiseExpression()) {
		
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				return analiseMoreParam();
			} else {
				return true;
			}

		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				return analiseMoreParam();
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	
	

	
	
	
	
	
	//<Reading_1> ::= Identifier<Array Verification><Attr><More Readings> 
	public static boolean analiseReading1() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				
				if(analiseArrayVerification()) {
				
					if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
						if(analiseAttr()) {
							
							if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
								return analiseMoreReadings();
							} else {
								return true;
							}
							
						} else {
							return false;
						}
						
					} else if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
						return analiseMoreReadings();
					} else {
						return true;
					}
					
				} else {
					return false;
				}
				
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				if(analiseAttr()) {
					
					if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
						return analiseMoreReadings();
					} else {
						return true;
					}
					
				} else {
					return false;
				}
				
			} else if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
				return analiseMoreReadings();
			} else {
				return true;
			}
		}
		
		return false;
	}

	//<More Readings> ::= ',' <Reading_1> | <>
	public static boolean analiseMoreReadings() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			
			return analiseReading1();
		}
		
		return false;
	}
	
	
	

		
	
	
	
	
	
	//<Writing_1> ::= Identifier<Array Verification><Attr><More Writings> | CadeCaracters<More Writings>                    
	public static boolean analiseWriting1() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				
				if(analiseArrayVerification()) {
					if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
						
						if(analiseAttr()) {
							if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
								return analiseMoreWritings();
							} else {
								return true;
							}
							
						} else {
							return false;
						}
						
					} else if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
						return analiseMoreWritings();
					} else {
						return true;
					}
					
				} else {
					return false;
				}
				
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				
				if(analiseAttr()) {
					if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
						return analiseMoreWritings();
					} else {
						return true;
					}
					
				} else {
					return false;
				}
				
			} else if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
				return analiseMoreWritings();
			} else {
				return true;
			}
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
				return analiseMoreWritings();
			} else {
				return true;
			}
		}
		
		return false;
	}
	
	//<More Writings> ::= ',' <Writing_1> | <>
	public static boolean analiseMoreWritings() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			
			return analiseWriting1();
		}
		
		return false;
	}
	

	
	
	
	
	
	//<Attr> ::= '.'Identifier<Array Verification><Attr> | <>
	public static boolean analiseAttr() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(".")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
				TokensFlow.next();
				
				if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
					if(analiseArrayVerification()) {
						
						if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
							return analiseAttr();
						} else {
							return true;
						}
						
					} else {
						return false;
					}
				} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					return analiseAttr();
				} else {
					return true;
				}
			}
		}
		
		return false;
	}
	
}
