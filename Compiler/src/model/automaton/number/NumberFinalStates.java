/**
 * 
 */
package model.automaton.number;

import model.automaton.FinalState;
import model.automaton.State;

/**
 * Enumeração com os estados finais possíveis para o autômato de número.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum NumberFinalStates implements FinalState {
	
	CORRECTNUMBER_FINALSTATE {

		@Override
		public State next(char character) {
			
			return CORRECTNUMBER_FINALSTATE;
		}
		
	},
	BADLYFORMEDNUMBER_FINALSTATE {

		@Override
		public State next(char character) {
			return BADLYFORMEDNUMBER_FINALSTATE;
		}
		
	}

}
