/**
 * 
 */
package model.automaton;

import model.automaton.delimiters.DelimitersStates;
import model.automaton.identifier.IdentifierStates;
import model.automaton.number.NumberStates;
import model.automaton.operator_comments.OperatorStates;
import model.automaton.string.StringStates;

/**
 * Classe que representa o autômato a ser utilizado durante a análise léxica dos caracteres
 * de entrada.
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Automaton {
	
	/** Estado atual do autômato **/
	private State currentState;
	
	/** Estado inicial do autômato **/
	private State initialState;
	
	/**
	 * Metódo privado para obter uma instância de autômato.
	 */
	public Automaton(State initialState) {
		currentState = initialState;
		this.initialState = initialState;
	}
	
	/**
	 * Obtém uma instância de autômato.
	 * 
	 * @param type tipo do autômato
	 * @return instância adequada do autômato
	 */
	public static Automaton start(int type) {
		
		switch(type) {
			case AutomatonConfiguration.NUMBER:
				return new Automaton(NumberStates.NUMBER_INITIAL_STATE);
			case AutomatonConfiguration.DELIMITERS:
				return new Automaton(DelimitersStates.DELIMITERS_INITIAL_STATE);
			case AutomatonConfiguration.IDENTIFIER:
				return new Automaton(IdentifierStates.INITIAL_STATE);
			case AutomatonConfiguration.STRING:
				return new Automaton(StringStates.STRING_INITIAL_STATE);
			default:
				return new Automaton(OperatorStates.OPERATOR_INITIAL_STATE);
		}
		
	}
	
	
	/**
	 * @return estado atual do autômato
	 */
	public State getCurrentState() {
		return currentState;
	}
	
	/**
	 * Configura o estado atual do autômato.
	 * 
	 * @param state novo estado atual
	 */
	public void setCurrentState(State state) {
		this.currentState = state;
	}
	
	/**
	 * Configura o próximo estado do autômato de acordo com o caractere.
	 * @param character próximo caractere
	 */
	public void next(char character) {
		currentState = currentState.next(character);
	}
	
	/**
	 * Verifica se o autômato se encontra no estado final.
	 * @return true, se o estado atual for final; false, caso contrário
	 */
	public boolean isFinalState() {
		return currentState instanceof FinalState;
	}
	
	/**
	 * Verifica se o autômato se encontra no estado morto.
	 * @return true, se o estado atual for morto; false, caso contrário
	 */
	public boolean isDeadState() {
		return currentState instanceof DeadState;
	}
	
	/**
	 * Reinicia o autômato.
	 */
	public void reset() {
		currentState = initialState;
	}


}
