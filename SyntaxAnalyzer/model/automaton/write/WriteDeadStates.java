package model.automaton.write;

import model.Token;
import model.automaton.DeadState;
import model.automaton.State;

/**
 * Enumera��o com o estado morto para o aut�mato do comando Write.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */

public enum WriteDeadStates implements DeadState{
	NOT_WRITE_FINALSTATE {
		
		@Override
		public State next(Token token) {
			return NOT_WRITE_FINALSTATE;
		}
	} 
}
