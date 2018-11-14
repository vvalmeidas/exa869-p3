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
 * Classe que representa o aut�mato a ser utilizado durante a an�lise l�xica dos caracteres
 * de entrada.
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Automaton {
	
	/** Estado atual do aut�mato **/
	private State currentState;
	
	/** Estado inicial do aut�mato **/
	private State initialState;
	
	/**
	 * Met�do privado para obter uma inst�ncia de aut�mato.
	 */
	public Automaton(State initialState) {
		currentState = initialState;
		this.initialState = initialState;
	}
	
	/**
	 * Obt�m uma inst�ncia de aut�mato.
	 * 
	 * @param type tipo do aut�mato
	 * @return inst�ncia adequada do aut�mato
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
	 * @return estado atual do aut�mato
	 */
	public State getCurrentState() {
		return currentState;
	}
	
	/**
	 * Configura o estado atual do aut�mato.
	 * 
	 * @param state novo estado atual
	 */
	public void setCurrentState(State state) {
		this.currentState = state;
	}
	
	/**
	 * Configura o pr�ximo estado do aut�mato de acordo com o caractere.
	 * @param character pr�ximo caractere
	 */
	public void next(char character) {
		currentState = currentState.next(character);
	}
	
	/**
	 * Verifica se o aut�mato se encontra no estado final.
	 * @return true, se o estado atual for final; false, caso contr�rio
	 */
	public boolean isFinalState() {
		return currentState instanceof FinalState;
	}
	
	/**
	 * Verifica se o aut�mato se encontra no estado morto.
	 * @return true, se o estado atual for morto; false, caso contr�rio
	 */
	public boolean isDeadState() {
		return currentState instanceof DeadState;
	}
	
	/**
	 * Reinicia o aut�mato.
	 */
	public void reset() {
		currentState = initialState;
	}


}
