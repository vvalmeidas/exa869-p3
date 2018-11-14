/**
 * 
 */
package model.automaton.delimiters;

import model.automaton.State;
import util.LexemeChecker;

/**
 * Enumera��o com o estado inicial e os estados intermedi�rios do aut�mato de delimitadores e as respectivas implementa��es da
 * l�gica de transi��o de estado adequada. 
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
