/**
 * 
 */
package model.automaton.string;

import model.automaton.FinalState;
import model.automaton.State;

/**
 * Enumera��o com os estados finais poss�veis para o aut�mato de cadeias de caracteres.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum StringFinalStates implements FinalState {
	
	CORRECT_STRING_FINALSTATE {

		@Override
		public State next(char character) {
			return CORRECT_STRING_FINALSTATE;
		}
		
	},
	BADLYFORMED_STRING_FINALSTATE {

		@Override
		public State next(char character) {
			return BADLYFORMED_STRING_FINALSTATE;
		}
		
	}

}
