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
	
	public static List<String> erros = new LinkedList<String>();
	
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
	
	public static boolean handleError(Token token, String expected, List<String> follow) {
		erros.add(token.getValue());
		
		while(TokensFlow.hasNext() && !follow.contains(token.getValue())) {
			TokensFlow.next();
		}
		
		if(!TokensFlow.isEmpty()) {
			if(First.check("Else", TokensFlow.getToken())) {
				return AnalyzerSecondary.analiseElseStatement(); 
			}
		}
		
		
		return false;
		
		
		
		
	}

}
