package controller;

import java.util.LinkedList;

import model.Token;
import model.TokensFlow;

public class AnalyzerSecondary {
	
	
	public static LinkedList<String> firstA = new LinkedList<String>() {{
		add("||");
	}};
	
	public static LinkedList<String> firstAndExp = new LinkedList<String>() {{
		add("-");
		add("--");
		add("!");
		add("(");
		add("++");
		add("false");
		add("true");
		add("IDENTIFICADOR");
		add("NUMERO");
	}};
	
	public static LinkedList<String> firstB = new LinkedList<String>() {{
		add("&&");
	}};
	
	public static LinkedList<String> firstC = new LinkedList<String>() {{
		add("OPERADOR_RELACIONAL");
	}};
	
	public static LinkedList<String> firstD = new LinkedList<String>() {{
		add("-");
		add("+");
	}};
	
	public static LinkedList<String> firstE = new LinkedList<String>() {{
		add("*");
		add("/");
	}};
	
	public static LinkedList<String> firstG = new LinkedList<String>() {{
		add("--");
		add("++");
	}};
	
	public static LinkedList<String> firstH = new LinkedList<String>() {{
		add("!");
	}};
	
	public static LinkedList<String> firstDoubleArray = new LinkedList<String>() {{
		add("[");
	}};
	
	public static LinkedList<String> firstArrayVerification = new LinkedList<String>() {{
		add("[");
	}};
	
	public static LinkedList<String> firstAttr = new LinkedList<String>() {{
		add(".");
	}};
	
	public static LinkedList<String> firstMoreReadings = new LinkedList<String>() {{
		add(",");
	}};
	
	
	public static boolean analiseA() { //<A> ::= '||' <Expression> | <> 
		if(TokensFlow.getToken().getValue().equals("||")) { 
			TokensFlow.next();
			return Analyzer.analiseExpression();
		}

		return false;
	}
	
	public static boolean analiseAndExp() {//<And Exp> ::= <Rel Exp> <B>   
		if(analiseRelExp()) {
			if(TokensFlow.hasNext() && AnalyzerSecondary.firstB.contains(TokensFlow.getToken().getValue())) {
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
			if(TokensFlow.hasNext() && AnalyzerSecondary.firstC.contains(TokensFlow.getToken().getValue())) {
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
			
			if(TokensFlow.hasNext() && AnalyzerSecondary.firstD.contains(TokensFlow.getToken().getValue())) {
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
			if(TokensFlow.hasNext() && AnalyzerSecondary.firstE.contains(TokensFlow.getToken().getValue())) {
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
			
			if(TokensFlow.hasNext() && firstH.contains(TokensFlow.getToken().getValue())) {
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
			if(TokensFlow.hasNext() && AnalyzerSecondary.firstG.contains(TokensFlow.getToken().getValue())) {
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
			
			if(TokensFlow.hasNext() && AnalyzerSecondary.firstH.contains(TokensFlow.getToken().getValue())) {
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
			
			if(TokensFlow.hasNext() && AnalyzerSecondary.firstArrayVerification.contains(TokensFlow.getToken().getValue())) {
				if(analiseArrayVerification()) {
					if(TokensFlow.hasNext() && AnalyzerSecondary.firstAttr.contains(TokensFlow.getToken().getValue())) {
						if(analiseAttr()) {
							
							if(TokensFlow.hasNext() && AnalyzerSecondary.firstMoreReadings.contains(TokensFlow.getToken().getValue())) {
								return analiseMoreReadings();
							}
						}
					} else {
						if(TokensFlow.hasNext() && AnalyzerSecondary.firstMoreReadings.contains(TokensFlow.getToken().getValue())) {
							return analiseMoreReadings();
						}
					}
				}
			} else {
				if(TokensFlow.hasNext() && AnalyzerSecondary.firstAttr.contains(TokensFlow.getToken().getValue())) {
					if(analiseAttr()) {
						
						if(TokensFlow.hasNext() && AnalyzerSecondary.firstMoreReadings.contains(TokensFlow.getToken().getValue())) {
							return analiseMoreReadings();
						}
					}
				} else {
					if(TokensFlow.hasNext() && AnalyzerSecondary.firstMoreReadings.contains(TokensFlow.getToken().getValue())) {
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
			if(analiseArrayIndex()) {
				if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("]")) {
					
					if(TokensFlow.hasNext() && AnalyzerSecondary.firstDoubleArray.contains(TokensFlow.getToken().getValue())) {
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
			if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("Identifier")) {
				return analiseAttr();
			}
		}
		return true; // se for vazio tem que verificar em quem chamou
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
			if(TokensFlow.hasNext() && AnalyzerSecondary.firstArrayVerification.contains(TokensFlow.getToken().getValue())) {
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
			if(TokensFlow.seeActual().getValue() == "++" || TokensFlow.seeActual().getValue() == "--") {
				TokensFlow.getToken();
				return true;
			} 
		}
		
		return false;
	}
	
	//<Normal Attribution2> ::= '=' <Normal Attribution3> | <Increment>  
	public static boolean analiseNormalAttribution2() {
		if(TokensFlow.hasNext()) {
			if(TokensFlow.getToken().getValue() == "=") {
				return analiseNormalAttribution3();
			} else if(analiseIncrement()) {
				return true;
			}
		}
		
		return false;
	}
	
	//<Normal Attribution3> ::= <Expression> | CadeCaracters
	public static boolean analiseNormalAttribution3() {
		if(TokensFlow.hasNext()) {
			if(TokensFlow.getToken().getTokenClass() == "CADEIA_DE_CARACTERES") {
				return true;
			} else {
				return Analyzer.analiseExpression();
			}
		}
		
		return false;

	}
	
	public static boolean analiseMoreReadings() {
		return true;
	}
	
	public static boolean analiseComplement() {
		return true;
	}
	
	
}
