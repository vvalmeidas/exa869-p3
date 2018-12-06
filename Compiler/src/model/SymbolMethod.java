/**
 * 
 */
package model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 *Classe que representa os símbolos dos métodos na tabela de símbolos
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class SymbolMethod extends Symbol{
	private String methodReturn;
	private List<MethodParameter> methodParameters = new LinkedList<MethodParameter>();
	
	public SymbolMethod(Token token) {
		super(token);
	}
}
