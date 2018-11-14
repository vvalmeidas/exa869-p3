/**
 * 
 */
package model.automaton;

/**
 * Interface para a implementa��o de um estado para o aut�mato
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public interface State {
	
	/**
	 * M�todo que ser� respons�vel por definir e retornar o pr�ximo estado do aut�mato
	 * a partir de um caractere de entrada.
	 * 
	 * @param character caractere de entrada
	 * @return pr�ximo estado do aut�mato
	 */
	public State next(char character);
	
}
