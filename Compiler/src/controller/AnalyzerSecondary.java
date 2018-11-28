package controller;

import java.util.LinkedList;

import model.First;
import model.Token;
import model.TokensFlow;
import model.Util;

public class AnalyzerSecondary {
	
	//<More Classes> ::= <Class Declaration><More Classes> | <>
	public static void analiseMoreClasses() {
		Analyzer.analiseClassDeclaration();
		if(TokensFlow.hasNext() && First.check("MoreClasses", TokensFlow.getToken())) {
			analiseMoreClasses();
			return;
		} else {
			return;
		}
		
	}
	
	//<Class Identification> ::= Identifier <Class Heritage> '{' <Class Body> '}'
	public static void analiseClassIdentification() {
		
		Util.handleTerminal("IDENTIFICADOR", false, false);
		
		if(TokensFlow.hasNext() && First.check("ClassHeritage", TokensFlow.getToken())) {
			analiseClassHeritage();
			Util.handleTerminal("{", true, false);
			analiseClassBody();
			Util.handleTerminal("}", true, false);
			return;
			
 		} else {
 			Util.handleTerminal("{", true, false);
			analiseClassBody();
			Util.handleTerminal("}", true, false);
			return;
		}
	}

	//<Class Heritage> ::= 'extends' Identifier | <>
	public static void analiseClassHeritage() {
		Util.handleTerminal("extends", true, false);
		Util.handleTerminal("IDENTIFIER", false, false);	
		return;
	}
	
	//<Class Body> ::= <Class Attributes> <Class Methods>
	public static void analiseClassBody() {
		analiseClassAttributes();
		if(TokensFlow.hasNext() && First.check("ClassMethods", TokensFlow.getToken())) {
			analiseClassMethods();
			return;
		} else {
			return;
		}
	}
	
	//<Class Attributes> ::= <Variable Declaration> 
	public static void analiseClassAttributes() {
		if(TokensFlow.hasNext() && First.check("VariableDeclaration", TokensFlow.getToken())) {
			Analyzer.analiseVariableDeclaration();
			return;
		} else {
			return;
		}
	}
	
	//<Class Methods> ::= <Method Declaration> | <>
	public static void analiseClassMethods() {
		Analyzer.analiseMethodDeclaration();
		return;
	}
		
	//<Constants> ::= Type <ConstAttribution> <More Constants> 
	public static void analiseConstants() {
		Util.handleTerminal("tipo", false, true);
		analiseConstAttribution();
		analiseMoreConstants();
		return;
	}
	
	//<More Constants> ::= ',' <ConstAttribution> <More Constants> | ';' <New Declaration> 
	public static void analiseMoreConstants() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
						
			if(analiseConstAttribution()) {

				analiseMoreConstants();
				return;
			}
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("NewDeclaration", TokensFlow.getToken())) {
				return analiseNewDeclaration();
			} else {
				return true;
			}
		}
	}
	
	//<New Declaration> ::= <Constants> | <>
	public static void analiseNewDeclaration() {
		analiseConstants();
		return;
	}

	
	//<ConstAttribution> ::= Identifier '=' <Value>
	public static void analiseConstAttribution() {
		Util.handleTerminal("IDENTIFICADOR", false, false);
		Util.handleTerminal("=", true, false);
		analiseValue();
		return;

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
	public static void analiseVariable2() {
		analiseName();
		
		if(TokensFlow.hasNext() && First.check("MoreVariables", TokensFlow.getToken())) {
			analiseMoreVariables();
			return;
		} else {
			return;
		}
	}
	
	//<More Variables> ::= <Variable> | <>
	public static boolean analiseMoreVariables() {
		return analiseVariable();
	}
	
	//<Name> ::= Identifier<Array Verification><More Names>
	public static void analiseName() {
		Util.handleTerminal("IDENTIFICADOR", false, false);
			
		if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {

			analiseArrayVerification();
			analiseMoreNames();
			return;

		} else {
			analiseMoreNames();
			return;
		}
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
	public static void analiseMoreMethods() {
		Analyzer.analiseMethodDeclaration();
		return;
	}
	
	//<Parameter Declaration> ::= <Parameter Declaration2> | <>
	public static void analiseParameterDeclaration() {
		analiseParameterDeclaration2();
		return;
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
	public static void analiseMoreParameters() {
		Util.handleTerminal(",", true, false);
		analiseParameterDeclaration2();
		return;
	}
	
	//<Array Verification> ::= '['<Array Index>']'<DoubleArray> | <>
	public static void analiseArrayVerification() {
		Util.handleTerminal("[", true, false);	
		analiseArrayIndex();
		Util.handleTerminal("]", true, false);
		if(TokensFlow.hasNext() && First.check("DoubleArray", TokensFlow.getToken())) {
			analiseDoubleArray();
			return;
		} else {
			return;
		}
	}	
	
	//<DoubleArray> ::= '['<Array Index>']' | <>
	public static void analiseDoubleArray() {
		Util.handleTerminal("[", true, false);
		analiseArrayIndex();
		Util.handleTerminal("]", true, false);
		return;
	}
	
	//<Array Index> ::= <Add Exp>
	public static void analiseArrayIndex() {
		analiseAddExp();
		return;
	}
	
	//<Return> ::= 'return' <Return1> 
	public static void analiseReturn() {
		Util.handleTerminal("return", true, false);
		analiseReturn1();
		
		return;
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
	public static void analiseElseStatement() {
		Util.handleTerminal("else", true, false);
		Util.handleTerminal("{", true, false);
		if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) { 
			Analyzer.analiseCommands();
			Util.handleTerminal("}", true, false);
			return;
		} else {
			Util.handleTerminal("}", true, false);
			return;
		}
			 
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
	public static void analiseAddExp() { 
		analiseMultExp();
		if(TokensFlow.hasNext() && First.check("D", TokensFlow.getToken())) {
			analiseD();
			return;
		} else {
			return;
		}
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
	public static void analiseMultExp() { 
		analiseNegExp();
			if(TokensFlow.hasNext() && First.check("E", TokensFlow.getToken())) {
				analiseE();
				return;
			} else {
				return;
			}
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
	
	//<Neg Exp>  ::= '-' <Exp Value> |  <Exp Value> <G> | '!' <Exp Value> | '++' <Exp Value> | '--'<Exp Value>
	public static boolean analiseNegExp() { 
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
					TokensFlow.next();
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
	public static void analiseParam2() {
		analiseComplement();
		return;
	}
	
	//<Complement> ::= '('<Param>')'
	public static void analiseComplement() {
		Util.handleTerminal("(", true, false);
		if(TokensFlow.hasNext() && First.check("Param", TokensFlow.getToken())) {		
			analiseParam();
			Util.handleTerminal(")", true, false);
			return;
		} else {
			Util.handleTerminal(")", true, false);
			return;
		}
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
	public static void analiseMoreParam() {
		Util.handleTerminal(",", true, false);
			analiseObrigatoryParam();
			return;
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
	public static void analiseReading1() {
		Util.handleTerminal("IDENTFICADOR", false, false);
			
		if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
			analiseArrayVerification();	
			if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				analiseAttr();
				if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
					analiseMoreReadings();
					return;
				} else {
					return;
				}
						
			} else if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
				analiseMoreReadings();
				return;
			}
				
		} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
			analiseAttr();
			if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
				analiseMoreReadings();
				return;
			} else {
				return;
			}
		} else if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
			analiseMoreReadings();
			return;
		} else {
			return;
		}
	}

	//<More Readings> ::= ',' <Reading_1> | <>
	public static void analiseMoreReadings() {
		Util.handleTerminal(",", true, false);
		analiseReading1();
		return;
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
	public static void analiseMoreWritings() {
		Util.handleTerminal(",", true, false);
		analiseWriting1();
		return;
	}
	
	//<Attr> ::= '.'Identifier<Array Verification><Attr> | <>
	public static void analiseAttr() {
		Util.handleTerminal(".", true, false);
		Util.handleTerminal("IDENTIFICADOR", false, false);
		
		if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
			analiseArrayVerification()) 
			if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				analiseAttr();
				return;
			} else {
				return;
			}
			
		} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
			analiseAttr();
			return;
		}
	}
	
}