/**
 * 
 */
package model;

import java.util.LinkedList;
import java.util.List;

/**
 * Representa um fluxo de tokens no contexto do analisador léxico.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class TokensFlow {
	
	public static List<Token> tokensSet = new LinkedList<Token>();
	
	public static void setTokensSet(List<Token> tokens) {
		tokensSet = tokens;
	}
	
	public static Token getNext() {
		return new Token(null, null, 0);
	}
	
	public static boolean hasNext() {
		return true;
	}
	
	public static boolean isEmpty() {
		return false;
	}
	

}
