/**
 * 
 */
package model.automaton.identifier;

import model.automaton.DeadState;
import model.automaton.State;

/**
 * Enumeração com o estado morto para o autômato de identificadores.
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
