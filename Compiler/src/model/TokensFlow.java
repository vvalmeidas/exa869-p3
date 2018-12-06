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
	
	/**
	 * Define o conjunto de tokens.
	 * @param tokens conjunto de tokens
	 */
	public static void setTokensSet(List<Token> tokens) {
		index = 0;
		tokensSet = tokens;
	}
	
	/**
	 * Obtém o token atual
	 * @return token atual
	 */
	public static Token getToken() {
		if(index >= tokensSet.size()) {
			return new Token("EOF", "Final do Arquivo", tokensSet.get(tokensSet.size()-1).getRow());
		}
		
		return tokensSet.get(index);
	}
	
	/**
	 * Verifica se há próximo token
	 * @return <code>true</code>, se há próximo; <code>false</code>, caso contrário
	 */
	public static boolean hasNext() {
		return tokensSet.size() > index;
	}
	
	/**
	 * Avança para o próximo token
	 */
	public static void next() {
		index++;
	}
	
	/**
	 * Retorna o token anterior
	 */
	public static Token back() {
		return tokensSet.get(index-1);
	}
	
	/**
	 * Verifica se não há mais tokens.
	 * @return <code>true</code>, se não há mais tokens; <code>false</code>, caso contrário
	 */
	public static boolean isEmpty() {
		return !hasNext();
	}
	

}
