/**
 * 
 */
package model;

/**
 *
 *Classe que representa os símbolos das constantes na tabela de símbolos
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class SymbolConstant extends Symbol{
	private String type;
	private String value; 
	
	public SymbolConstant(Token token) {
		super(token);
	}
	
}
