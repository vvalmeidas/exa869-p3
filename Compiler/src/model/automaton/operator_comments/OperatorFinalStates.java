/**
 * 
 */
package model.automaton.operator_comments;

import model.automaton.FinalState;
import model.automaton.State;

/**
 * Enumeração com os estados finais possíveis para o autômato de operadores.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum OperatorFinalStates implements FinalState {
	
	CORRECT_OPERATOR_ARITHMETIC_FINALSTATE {

		@Override
		public State next(char character) {
			return CORRECT_OPERATOR_ARITHMETIC_FINALSTATE;
		}
		
	},
	CORRECT_OPERATOR_LOGIC_FINALSTATE {

		@Override
		public State next(char character) {
			return CORRECT_OPERATOR_LOGIC_FINALSTATE;
		}
		
	},
	CORRECT_OPERATOR_RELATIONAL_FINALSTATE {

		@Override
		public State next(char character) {
			return CORRECT_OPERATOR_RELATIONAL_FINALSTATE;
		}
		
	},
	CORRECT_COMMENT_DELIMITER_FINALSTATE {
		
		@Override
		public State next(char character) {
			return CORRECT_COMMENT_DELIMITER_FINALSTATE;
		}
	},
	CORRECT_BLOCK_COMMENT_DELIMITER_FINALSTATE {
		@Override
		public State next(char character) {
			return CORRECT_BLOCK_COMMENT_DELIMITER_FINALSTATE;
		}
	}, 
	BADLYFORMED_OPERATOR_LOGIC_FINALSTATE {

		@Override
		public State next(char character) {
			return BADLYFORMED_OPERATOR_LOGIC_FINALSTATE;
		}
		
	}
	

}
