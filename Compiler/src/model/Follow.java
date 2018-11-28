/**
 * 
 */
package model;

import java.util.LinkedList;
import java.util.List;

import controller.Analyzer;
import controller.AnalyzerSecondary;

/**
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Follow {
	
	public static LinkedList<String> ClassDeclaration = new LinkedList<String>() {{
		add("class");
	}};
	
	
	public static boolean handleError(Token token, String expected, List<String> follow) {
		Util.errors.add(token.getValue());
		
		while(TokensFlow.hasNext() && (!follow.contains(token.getValue()) || TokensFlow.getToken().getTokenClass().equals("PALAVRA_RESERVADA"))) {
			TokensFlow.next();
		}
		
		if(!TokensFlow.isEmpty()) {
			
			if(First.check("ClassDeclaration", TokensFlow.getToken())) {
				return Analyzer.analiseClassDeclaration();
			}
			
			
			if(First.check("Else", TokensFlow.getToken())) {
				return AnalyzerSecondary.analiseElseStatement(); 
			}
		}
		
		
		return false;
		
		
		
		
	}

}
