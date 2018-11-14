/**
 * 
 */
package model.automaton.identifier;

import model.automaton.DeadState;
import model.automaton.State;

/**
 * Enumera��o com o estado morto para o aut�mato de identificadores.
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum IdentifierDeadState implements DeadState {
	
	NOTIDENTIFIER_FINALSTATE {

		@Override
		public State next(char character) {
			return NOTIDENTIFIER_FINALSTATE;
		}
		
	}

}
