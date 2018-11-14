/**
 * 
 */
package model.automaton.delimiters;

import model.automaton.FinalState;
import model.automaton.State;

/**
 * Enumera��o com os estados finais poss�veis para o aut�mato de delimitadores.
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum DelimitersFinalStates implements FinalState {
	
	CORRECTDELIMITER_FINALSTATE {

		@Override
		public State next(char character) {
			return CORRECTDELIMITER_FINALSTATE;
		}
		
	}

}
