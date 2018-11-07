package model.automaton.write;

import model.Token;
import model.automaton.State;


/**
 * Enumeração com o estado inicial e os estados intermediários do autômato do
 * comando Write e as respectivas implementações da lógica de transição de estado adequada. 
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */

public enum WriteStates implements State{
	WRITE_INITIAL_STATE {
		
		@Override
		public State next(Token token) {
			if(token.getValue().equals("write") ){
				return WRITE_OPENBRACKETS_STATE;
			}
			return WriteDeadStates.NOT_WRITE_FINALSTATE;
			
		}
	}, 
	
	WRITE_OPENBRACKETS_STATE {
		
		@Override
		public State next(Token token) {
			if(token.getValue().equals("(") ){
				return WRITE_IDENTIFIER_OR_CADECHARACTERES_STATE;
			}
			return WriteFinalStates.BADLYFORMED_WRITE_FINALSTATE;
			
		}
	},
	
	WRITE_IDENTIFIER_OR_CADECHARACTERES_STATE {
		
		@Override
		public State next(Token token) {
			if(token.getTokenClass().equals("Identifier")) {
				return WRITE_ARRAYVERIFICATION_OR_ATTRIBUTE_OR_MOREWRITINGS_STATE;
			}
			else if(token.getTokenClass().equals("CadeCharacters")) {
				return WRITE_MOREWRITINGS_OR_CLOSEBRACKETS_STATE;
			}
			return WriteFinalStates.BADLYFORMED_WRITE_FINALSTATE;
		}
	},
	
	WRITE_ARRAYVERIFICATION_OR_ATTRIBUTE_OR_MOREWRITINGS_STATE {
		
		@Override
		public State next(Token token) {
			//aqui teria que chamar o autômato de array verification e de chamada a atributo
			
			if(token.getValue().equals(",")) {
				return WRITE_IDENTIFIER_OR_CADECHARACTERES_STATE;
			}
			return WriteFinalStates.BADLYFORMED_WRITE_FINALSTATE;
		}
	},
	
	WRITE_MOREWRITINGS_OR_CLOSEBRACKETS_STATE {
		
		@Override
		public State next(Token token) {
			if(token.getValue().equals(",")) {
				return WRITE_IDENTIFIER_OR_CADECHARACTERES_STATE;
			}
			else if(token.getValue().equals(")")) {
				return WRITE_SEMICOLON_STATE;
			}
			else {
				return WriteFinalStates.BADLYFORMED_WRITE_FINALSTATE;
			}
		}
	},
	
	WRITE_SEMICOLON_STATE {
		
		@Override
		public State next(Token token) {
			if(token.getValue().equals(";")) {
				return WriteFinalStates.CORRECT_WRITE_FINALSTATE;
			}
			return WriteFinalStates.BADLYFORMED_WRITE_FINALSTATE;
		}
	}

}
