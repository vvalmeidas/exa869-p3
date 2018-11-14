package controller;

import model.TokensFlow;

public class AnalyzerSecondary {
	
	public static boolean a() {
		if(TokensFlow.getNext().getValue().equals("||")) { //<A> ::= '||' <Expression>
			nextToken();
			if(Analyzer.analiseExpression()) {
				return true;
			}
		}
		//caso <A> ::= <> teria que voltar para quem chamou expressões
		return false;
	}
	
	public static boolean andExp() {//<And Exp> ::= <Rel Exp> <B>   
		if(relExp()) {
			nextToken();		
		}
		return false;
	}
	
	public static boolean b() {		
		if(TokensFlow.getNext().getValue().equals("&&")) { //<B> ::= '&&' <And Exp>
			nextToken();
			if(andExp()) {
				return true;
			}
		}
		if(a()) {
			return true; //<B> ::= <>
		}
		return false;
	}
	
	public static boolean relExp() {//<Rel Exp>::= <Add Exp> <C>
		if(addExp()) {
			nextToken();			
			if(c()) {
				return true; 
			}
			return false;
		}
		return false;
	}
	
	public static boolean c() { //<C>
		if(TokensFlow.getNext().getTokenClass().equals("RelationalOperator")) { //<C>
			nextToken();
			if(relExp()) {
				return true;
			}
		}
		else if(b()) { //pq o <C> pode ser vazio
			return true;
		}
		return false;
	}
	
	public static boolean addExp() { //<Add Exp> ::= <Mult Exp> <D>
		if(multExp()) {
			nextToken();
			if(d()) {
				return true;
			}
		}	
		return false;
	}
	
	public static boolean d() { //<D> ::=
		if(TokensFlow.getNext().getValue().equals("+") || TokensFlow.getNext().getValue().equals("-")) {
			nextToken();
			if(addExp()) {
				return true;
			}
		}
		else if(c()){
			return true;
		}
		return false;
	}
	
	public static boolean multExp() { // <Mult Exp> ::= <Neg Exp> <E>
		if(negExp()) {
			nextToken();
			if(e()) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean e() { //<E>
		
		if(TokensFlow.getNext().getValue().equals("*")) {
			nextToken();
			if(multExp()) {
				return true;
			}
		}
		else if(TokensFlow.getNext().getValue().equals("/")) {
			nextToken();
			if(multExp()) {
				return true;
			}
		}
		else if(d()){  // <E> ::= <>
			return true;
		}
		return false;
	}
	
	public static boolean negExp() {
		//inicio <Neg Exp>
		if(TokensFlow.getNext().getValue().equals("-")) { //'-' <F>
			nextToken();
			//n terminal <F>
			if(TokensFlow.getNext().getValue().equals("-")) {
				nextToken();
				if(expValue()) {
					return true;
				}			
			}
			else if(expValue()) {
				return true;
			}
			//fim n terminal <F>
			
		}
		
		if(expValue()) {//<Exp Value> <G>
			nextToken();
			if(TokensFlow.getNext().getValue().equals("--") || TokensFlow.getNext().getValue().equals("++")) { // <G> ::= '--' | '++' | <>
				return true;
			}
			else { // <G> ::= <>
				e();
			}
		}
		
		if(TokensFlow.getNext().getValue().equals("!")) { //'!' <H><Exp Value>
			nextToken();
			//if() { // <H> ::= '!'<H> | <> 
				nextToken();
				if(expValue()) {
					return true;
				}
			//}
			else if(expValue()) {
				return true;
			}
		}		
		if(TokensFlow.getNext().getValue().equals("--") || TokensFlow.getNext().getValue().equals("++")) { // '++' <Exp Value> | '--'<Exp Value>
			nextToken();
			if(expValue()) {
				return true;
			}
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
	
	
}
