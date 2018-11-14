/**
 * 
 */
package model.automaton.string;

import model.automaton.State;
import util.LexemeChecker;

/**
 * Enumeração com o estado inicial e os estados intermediários do autômato de cadeia de
 * caracteres e as respectivas implementações da lógica de transição de estado adequada. 
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum StringStates implements State {
	
	STRING_INITIAL_STATE {

		@Override
		public State next(char character) {
				
			if(character == '"') {
				return STRING_READING_STATE;
			}
			
			return StringDeadState.NOT_STRING_FINALSTATE;
		}
		
	},
	STRING_READING_STATE {

		@Override
		public State next(char character) {
			
			if(character == '\\') {
				return STRING_AFTER_PIPE_STATE;
			} else if(LexemeChecker.isValidSymbol(character)) {
				return STRING_READING_STATE;
			} else if(character == '"') {
				return STRING_CLOSE_STATE;
			}  else if(LexemeChecker.isStringDelimiter(character)) {
				return StringFinalStates.BADLYFORMED_STRING_FINALSTATE;
			} 
			
			return BADLYFORMED_STRING_STATE;
		}
		
	},
	STRING_AFTER_PIPE_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isValidSymbol(character)) {
				return STRING_READING_STATE;
			} else if(character == '"') {
				return STRING_SPECIAL_SYMBOL_STATE;
			} else if(LexemeChecker.isStringDelimiter(character)) {
				return StringFinalStates.BADLYFORMED_STRING_FINALSTATE;
			}
			
			return BADLYFORMED_STRING_STATE;
		}
		
	},
	STRING_SPECIAL_SYMBOL_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isValidSymbol(character)) {
				return STRING_READING_STATE;
			} else if(LexemeChecker.isStringDelimiter(character)) {
				return StringFinalStates.CORRECT_STRING_FINALSTATE;
			} else if(character == '"') {
				return STRING_CLOSE_STATE;
			}
			
			return BADLYFORMED_STRING_STATE;
		}
		
	},
	STRING_CLOSE_STATE {

		@Override
		public State next(char character) {
			return StringFinalStates.CORRECT_STRING_FINALSTATE;
		}
		
	}, 
	BADLYFORMED_STRING_STATE {

		@Override
		public State next(char character) {
			
			if(LexemeChecker.isStringDelimiter(character)) {
				return StringFinalStates.BADLYFORMED_STRING_FINALSTATE;
			} else if(character == '"') {
				return BADLYFORMED_CLOSE_STRING_STATE;
			}
			
			return BADLYFORMED_STRING_STATE;
		}
		
	}, 
	BADLYFORMED_CLOSE_STRING_STATE {

		@Override
		public State next(char character) {
			return StringFinalStates.BADLYFORMED_STRING_FINALSTATE;
		}
		
	}

}
