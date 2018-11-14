/**
 * 
 */
package model.automaton.operator_comments;

import model.automaton.State;
import util.LexemeChecker;

/**
 * Enumeração com o estado inicial e os estados intermediários do autômato de operadores e as respectivas 
 * implementações da lógica de transição de estado adequada. 
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public enum OperatorStates implements State {
	OPERATOR_INITIAL_STATE {

		@Override
		public State next(char character) {
			if(LexemeChecker.isArithmeticOperator(character)) {
				if(character == '+') {
					return OPERATOR_ARITHMETIC_2PLUS_STATE;
				} else if(character == '-') {
					return OPERATOR_ARITHMETIC_2MINUS_STATE;
				} else if(character == '/') {
					return OPERATOR_COMMENT_INIT_STATE;
				} 
				
				return CORRECT_OPERATOR_ARITHMETIC_STATE;
			} else if(character == '!') {
				return OPERATOR_EXCLAMATION_SYMBOL_STATE;
			} else if(character == '=') {
				return OPERATOR_EQUAL_SYMBOL_STATE;
			} else if(character == '<') {
				return OPERATOR_LESS_SYMBOL_STATE;
			} else if(character == '>') {
				return OPERATOR_GREATER_SYMBOL_STATE;
			} else if(character == '&') {
				return OperatorStates.OPERATOR_AND_SYMBOL_STATE;
			} else if(character == '|') {
				return OperatorStates.OPERATOR_OR_SYMBOL_STATE;
			}
			
			return OperatorDeadState.NOT_OPERATOR_FINALSTATE;
		}
		
	},
	OPERATOR_ARITHMETIC_2PLUS_STATE {

		@Override
		public State next(char character) {
			if(character == '+') {
				return CORRECT_OPERATOR_ARITHMETIC_STATE;
			}
			
			return OperatorFinalStates.CORRECT_OPERATOR_ARITHMETIC_FINALSTATE;
		}
		
	},
	OPERATOR_ARITHMETIC_2MINUS_STATE {

		@Override
		public State next(char character) {
			if(character == '-') {
				return CORRECT_OPERATOR_ARITHMETIC_STATE;
			} else if(LexemeChecker.isDigit(character)){
				return OperatorDeadState.NOT_OPERATOR_FINALSTATE;
			}
			
			return OperatorFinalStates.CORRECT_OPERATOR_ARITHMETIC_FINALSTATE;
		}
		
	},
	OPERATOR_COMMENT_INIT_STATE {
		
		@Override
		public State next(char character) {
			if(character == '*') {
				return OPERATOR_BLOCK_COMMENT_LOOP_STATE;
			} else if(character == '/') {
				return OPERATOR_COMMENT_LOOP_STATE;
			}
			return OperatorFinalStates.CORRECT_OPERATOR_ARITHMETIC_FINALSTATE;
		}
	},
	OPERATOR_COMMENT_LOOP_STATE {
		
		@Override
		public State next(char character) {
			// se chegou ao fim da linha
			if(character == '\n') {
				return OperatorFinalStates.CORRECT_COMMENT_DELIMITER_FINALSTATE;
			}
			return OPERATOR_COMMENT_LOOP_STATE;
		}
	},
	OPERATOR_BLOCK_COMMENT_LOOP_STATE {
		
		@Override
		public State next(char character) {
			if(character == '*') {
				return OPERATOR_BLOCK_COMMENT_END_STATE;
			}
			return OPERATOR_BLOCK_COMMENT_LOOP_STATE;
		}
	},
	OPERATOR_BLOCK_COMMENT_END_STATE {
		
		@Override
		public State next(char character) {
			
			if(character == '/') {
				return CORRECT_BLOCK_COMMENT_DELIMITER_STATE;
			}
			return OPERATOR_BLOCK_COMMENT_LOOP_STATE;
		}
		
	},
	OPERATOR_EXCLAMATION_SYMBOL_STATE {

		@Override
		public State next(char character) {
			if(character == '=') {
				return CORRECT_OPERATOR_RELATIONAL_STATE;
			}
			
			return OperatorFinalStates.CORRECT_OPERATOR_LOGIC_FINALSTATE;
		}
		
	},
	OPERATOR_EQUAL_SYMBOL_STATE {

		@Override
		public State next(char character) {
			if(character == '=') {
				return CORRECT_OPERATOR_RELATIONAL_STATE;
			}
			
			return OperatorFinalStates.CORRECT_OPERATOR_RELATIONAL_FINALSTATE;
		}
		
	},
	OPERATOR_LESS_SYMBOL_STATE {

		@Override
		public State next(char character) {
			if(character == '=') {
				return CORRECT_OPERATOR_RELATIONAL_STATE;
			}
			
			return OperatorFinalStates.CORRECT_OPERATOR_RELATIONAL_FINALSTATE;
		}
		
	},
	OPERATOR_GREATER_SYMBOL_STATE {

		@Override
		public State next(char character) {
			if(character == '=') {
				return CORRECT_OPERATOR_RELATIONAL_STATE;
			}
			
			return OperatorFinalStates.CORRECT_OPERATOR_RELATIONAL_FINALSTATE;
		}
		
	},
	OPERATOR_AND_SYMBOL_STATE {
		
		@Override
		public State next(char character) {
			if(character == '&') {
				return CORRECT_OPERATOR_LOGIC_STATE;
			}
			
			return OperatorFinalStates.BADLYFORMED_OPERATOR_LOGIC_FINALSTATE;
		}
	},
	OPERATOR_OR_SYMBOL_STATE {
		
		@Override
		public State next(char character) {
			if(character == '|') {
				return CORRECT_OPERATOR_LOGIC_STATE;
			}
			
			return OperatorFinalStates.BADLYFORMED_OPERATOR_LOGIC_FINALSTATE;
		}
	},
	CORRECT_OPERATOR_ARITHMETIC_STATE {

		@Override
		public State next(char character) {
			return OperatorFinalStates.CORRECT_OPERATOR_ARITHMETIC_FINALSTATE;
		}
		
	}, 
	CORRECT_OPERATOR_LOGIC_STATE {

		@Override
		public State next(char character) {
			return OperatorFinalStates.CORRECT_OPERATOR_LOGIC_FINALSTATE;
		}
		
	},
	CORRECT_OPERATOR_RELATIONAL_STATE {

		@Override
		public State next(char character) {
			return OperatorFinalStates.CORRECT_OPERATOR_RELATIONAL_FINALSTATE;
		}
		
	},
	CORRECT_BLOCK_COMMENT_DELIMITER_STATE {
		
		@Override
		public State next(char character) {
			return OperatorFinalStates.CORRECT_BLOCK_COMMENT_DELIMITER_FINALSTATE;
		}
	}

}
