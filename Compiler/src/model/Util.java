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
	
	public static void handleTerminal(String terminal) {
		if(TokensFlow.hasNext()) {
			if(TokensFlow.getToken().getValue().equals(terminal)) {
				TokensFlow.next();
				return;
			} else {
				errors.add(terminal);
				return;
			}		
		}
	}


}
