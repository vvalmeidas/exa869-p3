package controller;

import java.util.LinkedList;

import model.First;
import model.Token;
import model.TokensFlow;

public class AnalyzerSecondary {
	
	
	public static boolean analiseA() { //<A> ::= '||' <Expression> | <> 
		if(TokensFlow.getToken().getValue().equals("||")) { 
			TokensFlow.next();
			return Analyzer.analiseExpression();
		}

		return false;
	}
	
	public static boolean analiseAndExp() {//<And Exp> ::= <Rel Exp> <B>   
		if(analiseRelExp()) {
			if(TokensFlow.hasNext() && First.check("B", TokensFlow.getToken())) {
				return analiseB();
			}
						
			return true;
		}
		
		return false;
	}
	
	public static boolean analiseB() {		
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("&&")) { //<B> ::= '&&' <And Exp> | <> 
			TokensFlow.next();
			return analiseAndExp();
		}
		
		return false;
	}
	
	public static boolean analiseRelExp() {//<Rel Exp>::= <Add Exp> <C>
		if(analiseAddExp()) {
			if(TokensFlow.hasNext() && First.check("C", TokensFlow.getToken())) {
				return analiseC();
			}
			
			return true;
		}
		
		return false;
	}
	
	
	public static boolean analiseC() { //<C> ::= RelationalOperator <Rel Exp> | <>
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("OPERADOR_RELACIONAL")) { //<C>
			TokensFlow.next();
			return analiseRelExp();
		}

		return false;
	}
	
	public static boolean analiseAddExp() { //<Add Exp> ::= <Mult Exp> <D>
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
		
		return false;
	}
	
	public static boolean analiseMultExp() { // <Mult Exp> ::= <Neg Exp> <E>
		if(analiseNegExp()) {
			if(TokensFlow.hasNext() && First.check("E", TokensFlow.getToken())) {
				return analiseE();
			}
			
			return true;
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
		
		return false;
	}
	
	public static boolean analiseNegExp() { //<Neg Exp>  ::= '-' <F> |  <Exp Value> <G> | '!' <H><Exp Value> | '++' <Exp Value> | '--'<Exp Value>
		//inicio <Neg Exp>
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("-")) { //'-' <F>
			TokensFlow.next();
			return analiseF();
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("!")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("H", TokensFlow.getToken())) {
				if(analiseH()) {
					return analiseExpValue();
				}
			}
			
			return true;
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
	
	//<F> ::= '-' <Exp Value> | <Exp Value>
	public static boolean analiseF() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("-")) {
			return analiseExpValue();
		} 
		
		return analiseExpValue();
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

	//<H> ::= '!'<H> | <> 
	public static boolean analiseH() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("!")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("H", TokensFlow.getToken())) {
				return analiseH();
			}
			
			return true;
		}
		
		return false;
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
		} else if(TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) { 
			//aqui teria que chamar a função de array verification e de chamada a atributo
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
	
	public static boolean analiseMoreReadings() {
		return true;
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
	
	
	
}
