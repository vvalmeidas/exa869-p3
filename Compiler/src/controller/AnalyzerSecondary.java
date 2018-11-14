package controller;

import model.TokensFlow;

public class AnalyzerSecondary {
	
	public static boolean a() {
		if(TokensFlow.getNext().getValue().equals("||")) { //<A> ::= '||' <Expression>
			nextToken();
			return Analyzer.analiseExpression();

		}
		//caso <A> ::= <> teria que voltar para quem chamou expressões
		return false;
	}
	
	public static boolean andExp() {//<And Exp> ::= <Rel Exp> <B>   
		if(relExp()) {
			return b();
		}
		return false;
	}
	
	public static boolean b() {		
		if(TokensFlow.getNext().getValue().equals("&&")) { //<B> ::= '&&' <And Exp>
			return andExp();
		}
		return a();
	}
	
	public static boolean relExp() {//<Rel Exp>::= <Add Exp> <C>
		if(addExp()) {
			return c();
		}
		return false;
	}
	
	public static boolean c() { //<C>
		if(TokensFlow.getNext().getTokenClass().equals("RelationalOperator")) { //<C>
			return relExp();
		}
		else { //pq o <C> pode ser vazio
			return b();
		}
		return false;
	}
	
	public static boolean addExp() { //<Add Exp> ::= <Mult Exp> <D>
		if(multExp()) {
			return d());
		}	
		return false;
	}
	
	public static boolean d() { //<D> ::=
		if(TokensFlow.getNext().getValue().equals("+") || TokensFlow.getNext().getValue().equals("-")) {
			return addExp();
		}
		else {
			return c();
		}
		return false;
	}
	
	public static boolean multExp() { // <Mult Exp> ::= <Neg Exp> <E>
		if(negExp()) {
			return e();
		}
		return false;
	}
	
	public static boolean e() { //<E>
		
		if(TokensFlow.getNext().getValue().equals("*")) {
			return multExp();
		}
		else if(TokensFlow.getNext().getValue().equals("/")) {
			return multExp();
		}
		else {  // <E> ::= <>
			return d();
		}
		return false;
	}
	
	public static boolean negExp() {
		//inicio <Neg Exp>
		if(TokensFlow.getNext().getValue().equals("-")) { //'-' <F>
			//n terminal <F>
			if(TokensFlow.getNext().getValue().equals("-")) {
				return expValue();			
			}
			else {
				return expValue();
			}
			//fim n terminal <F>
			
		}
		
		if(expValue()) {//<Exp Value> <G>
			if(TokensFlow.getNext().getValue().equals("--") || TokensFlow.getNext().getValue().equals("++")) { // <G> ::= '--' | '++' | <>
				return true;
			}
			else { // <G> ::= <>
				return e();
			}
		}
		
		if(TokensFlow.getNext().getValue().equals("!")) { //'!' <H><Exp Value>
			//if() { // <H> ::= '!'<H> | <> 
				return expValue();
			//}
			else {
				return expValue();
			}
		}		
		if(TokensFlow.getNext().getValue().equals("--") || TokensFlow.getNext().getValue().equals("++")) { // '++' <Exp Value> | '--'<Exp Value>
			return expValue();
		}			
		//fim <Neg Exp>		
		return false;
	}
	
	public static boolean expValue() { //<Exp Value> ::= Number |  '(' <Expression> ')' |  Identifier<Array Verification><Attr><Param2> | 'true' | 'false' 
		if(TokensFlow.getNext().getTokenClass().equals("Number")) {
			return true;
		}
		else if(TokensFlow.getNext().getValue().equals("(")) {
			if(Analyzer.analiseExpression()) {
				if(TokensFlow.getNext().getValue().equals(")")) {
					return true;
				}
			}
		}
		else if(TokensFlow.getNext().getTokenClass().equals("Identifier")) { 
			//aqui teria que chamar a função de array verification e de chamada a atributo
		}
		else if(TokensFlow.getNext().getValue().equals("true") || TokensFlow.getNext().getValue().equals("false")) {
			return true;
		}

		return false;
	}
	
	public static boolean analiseElse() {
		if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("else")) {

			if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("{")) {
	
				if(TokensFlow.hasNext() && true) { //commands
					TokensFlow.getNext(); //TIRAR ISSO AQUI
					
					if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals("}")) {
						return true;
					}
				}
			} 
		}
		
		return false;
	}
	
	//<Reading_1> ::= Identifier<Array Verification><Attr><More Readings> 
	public static boolean analiseReading1() {
		if(TokensFlow.hasNext() && TokensFlow.getNext().getTokenClass().equals("Identifier")) {
			if(analiseArrayVerification()) {
				if(analiseAttr()) {
					if(TokensFlow.hasNext() && TokensFlow.getNext().getValue().equals(",")) {
						return analiseReading1();
					}
				}
			}
		}
		
	}
	
	public static boolean analiseArrayVerification() {
		return true;
	}
	public static boolean analiseAttr() {
		return true;
	}
	
	
}
