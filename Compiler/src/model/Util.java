/**
 * 
 */
package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import controller.AnalyzerSecondary;

/**
 * Implementa métodos utéis ao programa.
 *  
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Util {
	
	/**
	 * Erros encontrados
	 */
	public static List<String> errors = new LinkedList<String>();
	public static List<String> semanticErrors = new LinkedList<String>();
	
	/**
	 * Verifica se token é TIPO (int, float, string, boolean ou void).
	 * @param token token
	 * @return <code>true</code>, se é tipo; <code>false</code>, caso contrário
	 */
	public static boolean isType(Token token) {
		if(token.getTokenClass().equals("PALAVRA_RESERVADA")) {
			if(token.getValue().equals("int") || 
				token.getValue().equals("float") ||
				token.getValue().equals("bool") ||
				token.getValue().equals("string") ||
				token.getValue().equals("void")) {
				
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Adiciona um erro.
	 * @param expected terminal esperado
	 */
	public static void addError(String expected) {
		errors.add("Erro na linha: " + TokensFlow.getToken().getRow() + ". Esperado: '" + expected + "' - Lido: '" + TokensFlow.getToken().getValue()  + "'");
	}
	
	/**
	 * Processa um terminal.
	 * @param terminal terminal
	 * @param value indica se é valor
	 * @param type indica se é tipo
	 * @param moreExpected outros tokens esperados
	 * @return <code>true</code>, é o terminal esperado; <code>false</code>, caso contrário
	 */
	public static boolean handleTerminal(String terminal, boolean value, boolean type, LinkedList<String> moreExpected) {
		if(TokensFlow.hasNext()) {
			if(type) {
				if(isType(TokensFlow.getToken())) {
					TokensFlow.next();
				} else {
					moreExpected.add("TIPO");
					addError(moreExpected.toString());
					return false;
				}
			} else if(value) {
				if(TokensFlow.getToken().getValue().equals(terminal)) {
					TokensFlow.next();
				} else {
					moreExpected.add(terminal);
					addError(moreExpected.toString());
					return false;
				}
			} else {
				if(TokensFlow.getToken().getTokenClass().equals(terminal)) {
					TokensFlow.next();
				} else {
					moreExpected.add(terminal);
					addError(moreExpected.toString());
					return false;
				}
			}
			
			return true;
		} else {
			if(terminal == null) {
				moreExpected.add("TIPO");
				addError(moreExpected.toString());
			} else {
				moreExpected.add(terminal);
				addError(moreExpected.toString());
			}
		}
		
		return false;
	}
	
	/**
	 * Processa um terminal.
	 * @param terminal terminal
	 * @param value indica se é valor
	 * @param type indica se é tipo
	 * @return <code>true</code>, é o terminal esperado; <code>false</code>, caso contrário
	 */
	public static boolean handleTerminal(String terminal, boolean value, boolean type) {
		if(TokensFlow.hasNext()) {
			if(type) {
				if(isType(TokensFlow.getToken())) {
					TokensFlow.next();
				} else {
					addError("TIPO");
					return false;
				}
			} else if(value) {
				if(TokensFlow.getToken().getValue().equals(terminal)) {
					TokensFlow.next();
				} else {
					
					addError(terminal);
					return false;
				}
			} else {
				if(TokensFlow.getToken().getTokenClass().equals(terminal)) {
					TokensFlow.next();
				} else {
					addError(terminal);
					return false;
				}
			}
			
			return true;
		} else {
			if(terminal == null) {
				addError("TIPO");
			} else {
				addError(terminal);
			}
		}
		
		return false;
	}
	
	public static void arrayIndexVerifier(Token token) {
		if(token.getTokenClass().equals("NUMERO")) {
			if(token.getValue().contains("-")) {
				addSemanticError("número negativo em índice de array");				
				System.out.println("número negativo em índice de array");
			}
		}
	}
	
	public static void addSemanticError(String erro) {
		semanticErrors.add("Erro semântico " + erro + " na linha " + TokensFlow.getToken().getRow());
	}
	


}
