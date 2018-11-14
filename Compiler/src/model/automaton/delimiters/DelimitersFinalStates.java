/**
 * 
 */
package model.automaton.delimiters;

import model.automaton.FinalState;
import model.automaton.State;

/**
 * Enumeração com os estados finais possíveis para o autômato de delimitadores.
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
