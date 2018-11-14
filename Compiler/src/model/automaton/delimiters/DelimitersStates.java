/**
 * 
 */
package model.automaton.delimiters;

import model.automaton.State;
import util.LexemeChecker;

/**
 * Enumeração com o estado inicial e os estados intermediários do autômato de delimitadores e as respectivas implementações da
 * lógica de transição de estado adequada. 
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum DelimitersStates implements State {
	
	DELIMITERS_INITIAL_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isDelimiter(character)) {
				return CORRECTDELIMITER_STATE;
			}
			
			return DelimiterDeadState.NOTDELIMITER_FINALSTATE;
			
		}
		
	},
	CORRECTDELIMITER_STATE {

		@Override
		public State next(char character) {
			return DelimitersFinalStates.CORRECTDELIMITER_FINALSTATE;
		}
		
	}

}
