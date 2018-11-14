/**
 * 
 */
package model.automaton;

/**
 * Interface para a implementação de um estado para o autômato
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public interface State {
	
	/**
	 * Método que será responsável por definir e retornar o próximo estado do autômato
	 * a partir de um caractere de entrada.
	 * 
	 * @param character caractere de entrada
	 * @return próximo estado do autômato
	 */
	public State next(char character);
	
}
