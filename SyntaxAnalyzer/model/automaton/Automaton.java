/**
 * 
 */
package model.automaton;

import java.util.Stack;

import model.Token;
import model.automaton.write.*;

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
	
	/** Pilha do aut�mato **/
	private Stack stack;
	
	/**
	 * Met�do privado para obter uma inst�ncia de aut�mato.
	 */
	public Automaton(State initialState) {
		currentState = initialState;
		this.initialState = initialState;
		stack = new Stack<>();
	}
	
	/**
	 * Obt�m uma inst�ncia de aut�mato.
	 * 
	 * @param type tipo do aut�mato
	 * @return inst�ncia adequada do aut�mato
	 */
	public static Automaton start(int type) {
		
		switch(type) {
			case AutomatonConfiguration.WRITE:
				return new Automaton(WriteStates.WRITE_INITIAL_STATE);
			default:
				return new Automaton(WriteStates.WRITE_INITIAL_STATE);
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
	 * Configura o pr�ximo estado do aut�mato de acordo com o token.
	 * @param character pr�ximo caractere
	 */
	public void next(Token token) {
		currentState = currentState.next(token);
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
