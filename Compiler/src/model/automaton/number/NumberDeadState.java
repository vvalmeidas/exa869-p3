/**
 * 
 */
package model.automaton.number;

import model.automaton.DeadState;
import model.automaton.State;

/**
 * Enumera��o com o estado morto para o aut�mato de n�mero.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum NumberDeadState implements DeadState {
	NOTNUMBER_FINALSTATE {

		@Override
		public State next(char character) {
			return NOTNUMBER_FINALSTATE;
		}
		
	}

}
