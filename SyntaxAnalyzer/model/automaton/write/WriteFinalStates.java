package model.automaton.write;

import model.Token;
import model.automaton.FinalState;
import model.automaton.State;

/**
 * Enumera��o com os estados finais poss�veis para o aut�mato do comando Write.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */

public enum WriteFinalStates implements FinalState{
	CORRECT_WRITE_FINALSTATE {

		@Override
		public State next(Token token) {
			return CORRECT_WRITE_FINALSTATE;
		}	
	},
	
	BADLYFORMED_WRITE_FINALSTATE {

		@Override
		public State next(Token token) {
			return BADLYFORMED_WRITE_FINALSTATE;
		}	
	}

}
