/**
 * 
 */
package model.automaton.number;

import model.automaton.State;
import util.LexemeChecker;

/**
 * Enumera��o com o estado inicial e os estados intermedi�rios do aut�mato de n�mero e as respectivas implementa��es da
 * l�gica de transi��o de estado adequada. 
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum NumberStates implements State {
	
	NUMBER_INITIAL_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isDigit(character)) {
				return DIGIT_STATE;
			} else if(character == '-'){
				return NEGATIVESPACES_STATE;
			}
			
			return NumberDeadState.NOTNUMBER_FINALSTATE;
		}
		
	},
	DIGIT_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isDigit(character)) {
				return DIGIT_STATE;
			} else if(character == '.') {
				return AFTERDOT_STATE;
			} else if(LexemeChecker.isNumberDelimiter(character)) {
				return NumberFinalStates.CORRECTNUMBER_FINALSTATE;
			}

			return BADLYFORMEDNUMBER_STATE;
			
		}
		
	},
	NEGATIVESPACES_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isSpace(character)) {
				return NEGATIVESPACES_STATE;
			} else if(LexemeChecker.isDigit(character)) {
				return DIGIT_STATE;
			}
			
			return NumberDeadState.NOTNUMBER_FINALSTATE;
		}
		
	},
	AFTERDOT_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isDigit(character)) {
				return AFTERDOT_STATE;
			} if(LexemeChecker.isNumberDelimiter(character)) {
				return NumberFinalStates.CORRECTNUMBER_FINALSTATE;
			}

			return BADLYFORMEDNUMBER_STATE;
		}
		
	},
	BADLYFORMEDNUMBER_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isNumberDelimiter(character)) {
				return NumberFinalStates.BADLYFORMEDNUMBER_FINALSTATE;
			}
			
			return BADLYFORMEDNUMBER_STATE;
		}
		
	}
	

}
