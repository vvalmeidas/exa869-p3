/**
 * 
 */
package model;

/**
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Util {
	
	public static boolean isType(Token token) {
		if(token.getTokenClass().equals("PALAVRA_RESERVADA")) {
			if(token.getValue().toLowerCase().equals("int") || 
				token.getValue().toLowerCase().equals("float") ||
				token.getValue().toLowerCase().toLowerCase().equals("bool") ||
				token.getValue().toLowerCase().equals("string") ||
				token.getValue().toLowerCase().equals("void")) {
				
				return true;
			}
		}
		
		return false;
	}

}
