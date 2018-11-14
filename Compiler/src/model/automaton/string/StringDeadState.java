/**
 * 
 */
package model.automaton.string;

import model.automaton.DeadState;
import model.automaton.State;

/**
 * Enumeração com o estado morto para o autômato de cadeia de caracteres.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum StringDeadState implements DeadState {
	
	NOT_STRING_FINALSTATE {

		@Override
		public State next(char character) {
			return NOT_STRING_FINALSTATE;
		}
		
	}

}
