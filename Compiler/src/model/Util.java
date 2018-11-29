/**
 * 
 */
package model;

import java.util.LinkedList;
import java.util.List;

import controller.AnalyzerSecondary;

/**
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Util {
	
	public static List<String> errors = new LinkedList<String>();
	
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
	
	public static void addError(String expected) {
		errors.add("Erro na linha: " + TokensFlow.getToken().getRow() + ". Esperado: '" + expected + "' - Lido: '" + TokensFlow.getToken().getValue()  + "'");
	}
	
	public static void handleTerminal(String terminal, boolean value, boolean type) {
		if(TokensFlow.hasNext()) {
			if(type) {
				if(isType(TokensFlow.getToken())) {
					TokensFlow.next();
					return;
				} else {
					addError("TIPO");
					return;
				}
			} else if(value) {
				if(TokensFlow.getToken().getValue().equals(terminal)) {
					TokensFlow.next();
					return;
				} else {
					addError(terminal);
					return;
				}
			} else {
				if(TokensFlow.getToken().getTokenClass().equals(terminal)) {
					TokensFlow.next();
					return;
				} else {
					addError(terminal);
					return;
				}
			}
		}
	}


}
