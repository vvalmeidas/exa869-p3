/**
 * 
 */
package model.automaton.delimiters;

import model.automaton.DeadState;
import model.automaton.State;

/**
 * Enumeração com o estado morto para o autômato de delimitadores.
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum DelimiterDeadState implements DeadState {
	
	NOTDELIMITER_FINALSTATE {

		@Override
		public State next(char character) {
			return NOTDELIMITER_FINALSTATE;
		}
		
	}

}
