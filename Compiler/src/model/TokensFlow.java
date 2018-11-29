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
	private static int index;
	
	public static void setTokensSet(List<Token> tokens) {
		index = 0;
		tokensSet = tokens;
	}
	
	public static Token getToken() {
		return tokensSet.get(index);
	}
	
	
	public static boolean hasNext() {
		return tokensSet.size() > index;
	}
	
	public static void back() {
		index--;
	}
	
	public static void next() {
		if(index < tokensSet.size()) {
			index++;
		}
	}
	
	public static boolean isEmpty() {
		return index == tokensSet.size();
	}
	

}
