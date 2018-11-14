/**
 * 
 */
package model.automaton.identifier;

import model.automaton.FinalState;
import model.automaton.State;

/**
 *
 * Enumera��o com os estados finais poss�veis para o aut�mato de reconhecimento de identificadores.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum IdentifierFinalStates implements FinalState{
	
	CORRECTIDENTIFIER_FINALSTATE {

		@Override
		public State next(char character) {
			
			return CORRECTIDENTIFIER_FINALSTATE;
		}
		
	},
	CORRECTIDENTIFIER_KEYWORD_FINALSTATE {

		@Override
		public State next(char character) {
			
			return CORRECTIDENTIFIER_KEYWORD_FINALSTATE;
		}
		
	},
	BADLYFORMEDIDENTIFIER_FINALSTATE {

		@Override
		public State next(char character) {
			return BADLYFORMEDIDENTIFIER_FINALSTATE;
		}
		
	}

}
