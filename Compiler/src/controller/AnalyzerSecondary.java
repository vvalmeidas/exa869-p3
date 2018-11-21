package controller;

import java.util.LinkedList;

import model.First;
import model.Token;
import model.TokensFlow;
import model.Util;

public class AnalyzerSecondary {
	
	//<More Classes> ::= <Class Declaration><More Classes> | <>
	
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
			}
			
			return true;
		}
		
		return false;
	}
	
	//<Class Attributes> ::= <Variable Declaration> 
	public static boolean analiseClassAttributes() {
		if(TokensFlow.hasNext() && First.check("VariableDeclaration", TokensFlow.getToken())) {
			return Analyzer.analiseVariableDeclaration();
		}
		
		return false;
	}
	
	//<Class Methods> ::= <Method Declaration> | <>
	public static boolean analiseClassMethods() {
		
		return false;
	}
	
	//<Constants> ::= <Constant> <More Constants>  
	public static boolean analiseConstants() {
		if(analiseConstant()) {
			if(TokensFlow.hasNext() && First.check("MoreConstants", TokensFlow.getToken())) {
				return analiseMoreConstants();
			}
			
			return true;
		}
		
		return false;
	}
	
	//<More Constants> ::= <> | <Constants>
	public static boolean analiseMoreConstants() {
		return analiseConstants();
	}
	
	//<Constant> ::= Type <ConstAttribution> | <More Attributions>
	public static boolean analiseConstant() {
		if(TokensFlow.hasNext() && Util.isType(TokensFlow.getToken())) {
			TokensFlow.next();
			
			return analiseConstAttribution();
		} else {
			return analiseMoreAttributions();
		}
		
	}
	
	//<More Attributions> ::= ',' <ConstAttribution> | ';'
	public static boolean analiseMoreAttributions() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			
			return analiseConstAttribution();
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
			TokensFlow.next();
			
			return true;
		}
		
		return false;
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
	
	
	//<Variable> ::= Type <Variable2> | Identifier <Variable2>
	public static boolean analiseVariable() {
		if(TokensFlow.hasNext()) {
			if(TokensFlow.getToken().getValue().toLowerCase().equals("int") 
					|| TokensFlow.getToken().getValue().toLowerCase().equals("float") 
					|| TokensFlow.getToken().getValue().toLowerCase().equals("bool")
					|| TokensFlow.getToken().getValue().toLowerCase().equals("string")
					|| TokensFlow.getToken().getValue().toLowerCase().equals("void")) 
			{
				TokensFlow.next();
				return analiseVariable2();
			} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
				TokensFlow.next();
				return analiseVariable2();
			}
		} 
		
		return false;
	}
	
	//<Variable2> ::= <Name> <More Variables>
	public static boolean analiseVariable2() {
		if(TokensFlow.hasNext() && analiseName()) {
			
			if(TokensFlow.hasNext() && First.check("MoreVariables", TokensFlow.getToken())) {
				return analiseMoreVariables();
			}
			
			return true;
		}
		
		return false;
	}
	
	//<More Variables> ::= <Variable> | <>
	public static boolean analiseMoreVariables() {
		if(TokensFlow.hasNext() && analiseVariable()) {
			return true;
		}
		
		return false;
	}
	
	//<Name> ::= Identifier<Array Verification><More Names>
	public static boolean analiseName() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				if(analiseArrayVerification()) {
					return analiseMoreNames();
				}
			} else if(analiseArrayVerification()) {
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
	
	
	public static boolean analiseRelExp() {//<Relational Exp> ::= RelationalOperator <Add Exp> <Logical Exp> | <Logical Exp>
		if(TokensFlow.hasNext() && (TokensFlow.getToken().getValue().equals("==") ||
			TokensFlow.getToken().getValue().equals(">") ||
			TokensFlow.getToken().getValue().equals("<") ||
			TokensFlow.getToken().getValue().equals(">=") ||
			TokensFlow.getToken().getValue().equals("<=") ||
			TokensFlow.getToken().getValue().equals("!="))) {				
			
				if(analiseAddExp()) {
					return analiseLogicalExp();
				}
		}
		
		else {
			
			return analiseLogicalExp();
		}
		
		return false;
	}
	
	//<Logical Exp> ::= '||' <Expression> | '&&' <Expression> | <>  
	public static boolean analiseLogicalExp() {
		
		if(TokensFlow.hasNext() && (TokensFlow.getToken().getValue().equals("||") || TokensFlow.getToken().getValue().equals("&&"))) {
			return Analyzer.analiseExpression();
		}
		
		return true; //MUDAR PARA TRUE?
	}

	//<Add Exp> ::= <Mult Exp> <D>
	public static boolean analiseAddExp() { 		
		if(analiseMultExp()) {
			
			if(TokensFlow.hasNext() && First.check("D", TokensFlow.getToken())) {	
				
				return analiseD();
			}
			
			return true;
		}	
		
		return false;
	}
	
	public static boolean analiseD() { //<D> ::= '+' <Add Exp> | '-' <Add Exp> | <>
		if(TokensFlow.hasNext() && (TokensFlow.getToken().getValue().equals("+") || TokensFlow.getToken().getValue().equals("-"))) {
			TokensFlow.next();
			return analiseAddExp();
		}
		
		return true;
	}
	
	public static boolean analiseMultExp() { // <Mult Exp> ::= <Neg Exp> <E>
		if(analiseNegExp()) {
			
				return analiseE();
		}
		
		return false;
	}
	
	public static boolean analiseE() { //<E> ::= '*' <Mult Exp> | '/' <Mult Exp> | <>
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("*")) {
			TokensFlow.next();
			return analiseMultExp();
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("/")) {
			TokensFlow.next();
			return analiseMultExp();
		}
		
		return true;
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
			
		} else if(analiseExpValue()) {
			
			if(TokensFlow.hasNext() && First.check("G", TokensFlow.getToken())) {
				return analiseG();
			}
			
			return true;
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
		
		return true;
	}
	
	public static boolean analiseExpValue() { //<Exp Value> ::= Number |  '(' <Expression> ')' |  Identifier<Array Verification><Attr><Param2> | 'true' | 'false' 
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("NUMERO")) {
			TokensFlow.next();
			return true;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
			if(Analyzer.analiseExpression()) {
				if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
					return true;
				}
			}
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) { 
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				
				if(TokensFlow.hasNext() && analiseArrayVerification()) {
					
					if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
						
						if(TokensFlow.hasNext() && analiseAttr()) {
							
							if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
								return analiseParam2();
							}
						}
						
					} else if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
						return analiseParam2();
					}
				} 
				
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				if(TokensFlow.hasNext() && analiseAttr()) {
					
					if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
						return analiseParam2();
					}
				}
			} else if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
				return analiseParam2();
			}
			
			return true;
		} else if(TokensFlow.hasNext() && (TokensFlow.getToken().getValue().equals("true") || TokensFlow.getToken().getValue().equals("false"))) {
			TokensFlow.next();
			return true;
		}

		return false;
	}
	
	public static boolean analiseElse() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("else")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("{")) {
				TokensFlow.next();

				if(TokensFlow.hasNext() && true) { //commands
					TokensFlow.next(); //TIRAR ISSO AQUI
					
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("}")) {
						TokensFlow.next();
						return true;
					}
				}
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
							}
						}
					} else {
						if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
							return analiseMoreReadings();
						}
					}
				}
			} else {
				if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					if(analiseAttr()) {
						
						if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
							return analiseMoreReadings();
						}
					}
				} else {
					if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
						return analiseMoreReadings();
					}
				}
			}
			
			return true;
			

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
					}
					
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
	
	//<DoubleArray> ::= '['<Array Index>']' | <>
	public static boolean analiseDoubleArray() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("[")) {
			if(analiseArrayIndex()) {
				if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("]")) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	//<Attr> ::= '.'Identifier<Array Verification><Attr> | <>
	public static boolean analiseAttr() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(".")) {
			if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
				
				if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					return analiseAttr();
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	//<Verif> ::= <Normal Attribution2> | <Complement>
	//<Normal Attribution2> ::= '=' <Normal Attribution3> | <Increment>                  
	//<Normal Attribution3>  ::=  <Expression> | CadeCaracters
	public static boolean analiseVerif() {
		
		if(analiseNormalAttribution2()) {
			return true;
		} else if(analiseComplement()) {
			return true;
		}
		
		return false;
	}
	
	//<Return1> ::= Identifier <Array Verification> | <Value>
	public static boolean return1() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				return analiseArrayVerification();
			}
			
			return true;
		} else if(analiseValue()) {
			return true;
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
		if(TokensFlow.hasNext()) {
			
			if(TokensFlow.getToken().getValue() == "=") {
				TokensFlow.next();
				
				return analiseNormalAttribution3();
			} 
			
			return analiseIncrement();
		}
		
		return false;
	}
	
	//<Normal Attribution3> ::= <Expression> | CadeCaracters
	public static boolean analiseNormalAttribution3() {
		if(TokensFlow.hasNext()) {
			if(TokensFlow.getToken().getTokenClass() == "CADEIA_DE_CARACTERES") {
				TokensFlow.next();
				
				return true;
			} 
			
			return Analyzer.analiseExpression();
		}
		
		return false;

	}
	
	//<More Readings> ::= ',' <Reading_1> | <>
	public static boolean analiseMoreReadings() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			return analiseReading1();
		}
		
		return false;
	}
	
	//<Param2>::= <Complement> | <>
	public static boolean analiseParam2() {
		
		if(TokensFlow.hasNext()) {
			return analiseComplement();
		}
		
		return false;
	}
	
	//<Complement> ::= '('<Param>')'
	public static boolean analiseComplement() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
			TokensFlow.next();

			if(TokensFlow.hasNext() && First.check("Param", TokensFlow.getToken())) {
				if(analiseMoreParam()) {
					if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
						TokensFlow.next();
						
						return true;
					}
				} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(")")) {
					TokensFlow.next();
					return true;
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
			}
			
			return true;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				return analiseMoreParam();
			}
			
			return true;
		}
		
		return false;
	}
	
	//<MoreParam> ::= ','<ObrigatoryParam> | <>
	public static boolean analiseMoreParam() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			return analiseObrigatoryParam();
		}
		
		return false;
	}
	
	
	//<ObrigatoryParam> ::= <Expression><MoreParam> | CadeCaracters<MoreParam>
	public static boolean analiseObrigatoryParam() {
		if(Analyzer.analiseExpression()) {
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				return analiseMoreParam();
			}
			
			return true;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				return analiseMoreParam();
			}
			
			return true;
		}
		
		return false;
	}

	
	//<Writing_1> ::= Identifier<Array Verification><Attr><More Writings> | CadeCaracters<More Writings>                    
	public static boolean analiseWriting1() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				
				if(TokensFlow.hasNext() && analiseArrayVerification()) {
					if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
						
						if(TokensFlow.hasNext() && analiseAttr()) {
							if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
								return analiseMoreWritings();
							}
						}
						
					} else if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
						return analiseMoreWritings();
					}
					
				} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					
					if(TokensFlow.hasNext() && analiseAttr()) {
						if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
							return analiseMoreWritings();
						}
					}
					
				} else if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
					return analiseMoreWritings();
				}
				
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				
				if(TokensFlow.hasNext() && analiseAttr()) {
					if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
						return analiseMoreWritings();
					}
				}
				
			} else if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
				return analiseMoreWritings();
			}
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
				return analiseMoreWritings();
			}
		}
		
		
		return false;
	}
	
	//<More Writings> ::= ',' <Writing_1> | <>
	public static boolean analiseMoreWritings() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && analiseWriting1()) {
				return true;
			}
		}
		
		return false;
	}

	
		
	
}
