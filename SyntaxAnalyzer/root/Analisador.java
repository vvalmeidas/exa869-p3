package root;

import model.Token;

public class Analisador {
	private Token token;
	
	public Token nextToken() {
		return null;
	}
	
	public boolean analiseExpressions() { //<Expression> ::= <And Exp> <A>
		if(andExp()) {
			nextToken();
			if(token.getValue().equals("||")) { //<A> ::= '||' <Expression>
				nextToken();
				if(analiseExpressions()) {
					return true;
				}
			}
			return true; //<A> ::= <>
		}
		
		return false;
	}
	
	public boolean andExp() {//<And Exp> ::= <Rel Exp> <B>   
		if(relExp()) {
			nextToken();
			if(token.getValue().equals("&&")) { //<B> ::= '&&' <And Exp>
				nextToken();
				if(andExp()) {
					return true;
				}
			}
			return true; //<B> ::= <>
		}
		return false;
	}
	public boolean relExp() {//<Rel Exp>::= <Add Exp> <C>
		if(addExp()) {
			nextToken();
			if(token.getTokenClass().equals("RelationalOperator")) { //<C>
				nextToken();
				if(relExp()) {
					return true;
				}
			}
			return true; //pq o <C> pode ser vazio
		}
		return false;
	}
	public boolean addExp() { //<Add Exp> ::= <Mult Exp> <D>
		if(multExp()) {
			nextToken();
			if(token.getValue().equals("+") || token.getValue().equals("-")) {
				nextToken();
				if(addExp()) {
					return true;
				}
			}
			return true; // pq <D> pode ser vazio
		}
		
		return false;
	}
	
	public boolean multExp() { // <Mult Exp> ::= <Neg Exp> <E>
		if(negExp()) {
			nextToken();
			//inicio <E>
			if(token.getValue().equals("*")) {
				nextToken();
				if(multExp()) {
					return true;
				}
			}
			if(token.getValue().equals("/")) {
				nextToken();
				if(multExp()) {
					return true;
				}
			}
			return true; // pq <E> pode ser vazio
			//fim <E>
		}
		return false;
	}
	
	public boolean negExp() {
		//inicio <Neg Exp>
		if(token.getValue().equals("-")) { //'-' <F>
			nextToken();
			//n terminal <F>
			if(token.getValue().equals("-")) {
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
			if(token.getValue().equals("--") || token.getValue().equals("++")) { //'--' | '++' | <>
				return true;
			}
		}
		
		if(token.getValue().equals("!")) { //'!' <H><Exp Value>
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
		
		if(token.getValue().equals("--") || token.getValue().equals("++")) { // '++' <Exp Value> | '--'<Exp Value>
			nextToken();
			if(expValue()) {
				return true;
			}
		}
			
		//fim <Neg Exp>
		
		
		return false;
	}
	
	private boolean expValue() { //<Exp Value> ::= Number |  '(' <Expression> ')' |  Identifier<Array Verification><Attr><Param2> | 'true' | 'false' 
		if(token.getTokenClass().equals("Number")) {
			return true;
		}
		else if(token.getValue().equals("(")) {
			if(analiseExpressions()) {
				if(token.getValue().equals(")")) {
					return true;
				}
			}
		}
		else if(token.getTokenClass().equals("Identifier")) { 
			//aqui teria que chamar a função de array verification e de chamada a atributo
		}
		else if(token.getValue().equals("true") || token.getValue().equals("false")) {
			return true;
		}

		return false;
	}
	
	public boolean analiseWrite() {
		if(token.getValue().equals("write")) {
			nextToken();
				
			if(token.getValue().equals("(") ) {
				token = nextToken();
					
				while(token.getTokenClass().equals("Identificador")  || token.getTokenClass().equals("CadeCharacters")) {
					if(token.getTokenClass().equals("Identifier")) {
						token = nextToken();
						//aqui teria que chamar a função de array verification e de chamada a atributo
							
						if(token.getValue().equals(",")) {
							token = nextToken();
						}
						else if(token.getValue().equals(")")) {
							token = nextToken();
								
							if(token.getValue().equals(";")) {
								return true;
							}
							else {
								return false;
							}
						}
						else {
							return false;
						}
					}
						
					else if(token.getTokenClass().equals("CadeCharacters")) {
						token = nextToken();
							
						if(token.getValue().equals(",")) {
								
							token = nextToken();
						}
						else if(token.getValue().equals(")")) {
							token = nextToken();
								
							if(token.getValue().equals(";")) {
								return true;
							}
							else {
								return false;
							}
						}
						else {
							return false;
						}
					}			
				}								
			}
			return false;
		}
		return false;
	}	
}
