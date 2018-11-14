/**
 * 
 */
package model.automaton.identifier;

import model.automaton.State;
import util.LexemeChecker;

/**
 * Enumeração com o estado inicial e os estados intermediários do autômato de identificadores e as respectivas 
 * implementações da lógica de transição de estado adequada.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum IdentifierStates implements State{

	INITIAL_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isLetter(character)) {
				return LETTER_STATE;
			}
			
			return IdentifierDeadState.NOTIDENTIFIER_FINALSTATE;
		}
		
	},
	LETTER_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isLetter(character) || LexemeChecker.isDigit(character) || LexemeChecker.isUnderline(character)) {
				return LETTER_STATE;
			} else if(LexemeChecker.isIdentifierDelimiter(character)){
				return IdentifierFinalStates.CORRECTIDENTIFIER_FINALSTATE;
			}
			
			return BADLYFORMEDIDENTIFIER_STATE;
		}
		
	},
	BADLYFORMEDIDENTIFIER_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isIdentifierDelimiter(character)) {
				return IdentifierFinalStates.BADLYFORMEDIDENTIFIER_FINALSTATE;
			}
			
			return BADLYFORMEDIDENTIFIER_STATE;
		}
		
	}

}
