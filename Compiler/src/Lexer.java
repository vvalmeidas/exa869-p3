import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.automaton.Automaton;
import model.automaton.AutomatonConfiguration;
import model.automaton.FinalState;
import model.automaton.State;
import model.Token;
import model.Error;
import model.automaton.delimiters.DelimiterDeadState;
import model.automaton.delimiters.DelimitersFinalStates;
import model.automaton.delimiters.DelimitersStates;
import model.automaton.identifier.IdentifierFinalStates;
import model.automaton.number.NumberFinalStates;
import model.automaton.operator_comments.OperatorDeadState;
import model.automaton.operator_comments.OperatorFinalStates;
import model.automaton.string.StringFinalStates;
import model.automaton.string.StringStates;
import util.LexemeChecker;

/**
 * 
 */

/**
 * Classe que implementa os principais m�todos para realiza��o da an�lise l�xica.
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Lexer {
	
	private Automaton string;
	private Automaton number;
	private Automaton delimiter;
	private Automaton identifier;
	private Automaton operator;
	private List<Token> tokens = new LinkedList<Token>();
	private List<Error> errors = new LinkedList<Error>();
	
	/**
	 * Obt�m uma inst�ncia dessa classe
	 */
	public Lexer() {
		startAutomatons();
	}

	/**
	 * Realiza a an�lise l�xica
	 * 
	 * @param source string contendo o programa fonte
	 */
	public void initialize(String source) {
		char[] sourceChar = source.toCharArray();
		int row = 1;
		int blockComment = 1;
		boolean isBlockComment = false;
		StringBuilder lexeme = new StringBuilder();

		for(int i = 0; i < sourceChar.length; i++) {
			
			if(sourceChar[i] == 10) {
				row++;
			}
			
			lexeme.append(sourceChar[i]);
			
			if(!string.isDeadState()) {
				string.next(sourceChar[i]);
			}
			
			if(!number.isDeadState()) {
				number.next(sourceChar[i]);
			}
			
			if(!delimiter.isDeadState()) {
				delimiter.next(sourceChar[i]);
			}
			
			if(!identifier.isDeadState()) {
				identifier.next(sourceChar[i]);
			}
			
			if(!operator.isDeadState()) {
				operator.next(sourceChar[i]);
			}
			
			if(!isBlockComment && operator.getCurrentState().toString().equals("OPERATOR_BLOCK_COMMENT_LOOP_STATE")) {
				blockComment = row;
				isBlockComment = true;
			}
			
			if(theresAnyFinalState()) {
				if(!(operator.isFinalState() && operator.getCurrentState().toString().equals("CORRECT_OPERATOR_ARITHMETIC_FINALSTATE") && !number.isDeadState())) {
					char lastChar = lexeme.toString().charAt(lexeme.length() - 1);
					
					if(lastChar == '\n') {
						row--;
					} 
					
					lexeme.deleteCharAt(lexeme.length() - 1);
					
					i--;
					
					if(identifier.isFinalState() && LexemeChecker.isKeyWord(lexeme.toString())) {
						identifier.setCurrentState(IdentifierFinalStates.CORRECTIDENTIFIER_KEYWORD_FINALSTATE);
					} 
					
					State finalState = getTheFinalState();
					
					if(isErrorState(finalState)) {
						errors.add(new Error(getErrorDescription(finalState), lexeme.toString(), row));
					} else {
						if(isBlockComment) {
							isBlockComment = false;
						} else if(!isCommentState(finalState)) {
							tokens.add(new Token(getTokenType(finalState), lexeme.toString(), row));
						}
					}

					
					startAutomatons();
					lexeme = new StringBuilder();
				} else {
					operator.setCurrentState(OperatorDeadState.NOT_OPERATOR_FINALSTATE);
				}
			} else if(isEveryAutomatonDead()) {
				char unknownChar = lexeme.toString().charAt(0);
			
				
				if(!LexemeChecker.isSpaceOrLineBreak(unknownChar)) {
					errors.add(new Error("Caractere desconhecido", lexeme.toString(), row));
				}
				
				lexeme = new StringBuilder();
				startAutomatons();
			}
			
			if(i == sourceChar.length-1) {
				if(operator.getCurrentState().toString().equals("OPERATOR_BLOCK_COMMENT_LOOP_STATE")) {
					//coment�rio de bloco mal formado
					errors.add(new Error("Coment�rio de bloco mal formado", lexeme.toString(), blockComment));
					operator.setCurrentState(OperatorDeadState.BADLY_FORMED_OPERATOR_BLOCK_COMMENT_END_STATE);
				}
			}
			
		}
		
		System.out.println("Arquivo analisado!");
	
	}
	
	/**
	 * Inicia todos os aut�matos
	 */
	private void startAutomatons() {
		string = Automaton.start(AutomatonConfiguration.STRING);
		number = Automaton.start(AutomatonConfiguration.NUMBER);
		delimiter = Automaton.start(AutomatonConfiguration.DELIMITERS);
		identifier = Automaton.start(AutomatonConfiguration.IDENTIFIER);
		operator = Automaton.start(AutomatonConfiguration.OPERATOR);

	}
	
	/**
	 * Verifica se todos os aut�matos est�o em estado morto.
	 * 
	 * @return true, caso todos os aut�matos estejam em estado morto; false, caso contr�rio
	 */
	private boolean isEveryAutomatonDead() {
		return string.isDeadState() && number.isDeadState() && delimiter.isDeadState() && identifier.isDeadState() && operator.isDeadState();
	}
	
	/**
	 * Verifica se existe algum aut�mato est� em estado final.
	 * 
	 * @return true, caso exista algum aut�mato em estado final; false, caso contr�rio
	 */
	private boolean theresAnyFinalState() {
		return string.isFinalState() || number.isFinalState() || delimiter.isFinalState() || identifier.isFinalState() || operator.isFinalState();
	}
	
	/**
	 * Obt�m o estado final de um dos aut�matos.
	 * 
	 * @return estado final do aut�mato
	 */
	private State getTheFinalState() {
		if(string.isFinalState()) {
			return string.getCurrentState();
		} else if(number.isFinalState()) {
			return number.getCurrentState();
		} else if(delimiter.isFinalState()) {
			return delimiter.getCurrentState();
		} else if(identifier.isFinalState()) {
			return identifier.getCurrentState();
		} else {
			return operator.getCurrentState();
		}
	}
	
	
	/**
	 * Verifica se o estado � um estado de erro.
	 * 
	 * @param state estado a ser verificado
	 * @return true, se o estado for um estado de erro; false, caso contr�rio
	 */
	private boolean isErrorState(State state) {		
		if(string.isFinalState()) {
			return state == StringFinalStates.BADLYFORMED_STRING_FINALSTATE;
		} else if(number.isFinalState()) {
			return state == NumberFinalStates.BADLYFORMEDNUMBER_FINALSTATE;
		} else if(identifier.isFinalState()) {
			return state == IdentifierFinalStates.BADLYFORMEDIDENTIFIER_FINALSTATE;
		} else if(operator.isFinalState()) {
			return state == OperatorFinalStates.BADLYFORMED_OPERATOR_LOGIC_FINALSTATE;
		}
		
		return false;
	}
	
	/**
	 * 	Obt�m a descri��o do erro.
	 * 
	 * @param state estado de erro
	 * @return descri��o para o erro, caso o estado seja de erro; null, caso contr�rio
	 */
	private String getErrorDescription(State state) {
		if(string.isFinalState()) {
			return "Cadeia de caracteres mal formada";
		} else if(number.isFinalState()) {
			return "Numero mal formado";
		} else if(identifier.isFinalState()) {
			return "Identificador mal formado";
		} else if(operator.isFinalState()) {
			if(operator.getCurrentState() == OperatorFinalStates.BADLYFORMED_OPERATOR_LOGIC_FINALSTATE) {
				return "Operador logico mal formado";
			}
		}
		
		return null;
	}
	
	/**
	 * Checa se o estado em quest�o est� relacionado a coment�rio
	 * 
	 * @param state estado em quest�o
	 * @return true, se o estado for relacionado a coment�rio; false, caso contr�rio
	 */
	private boolean isCommentState(State state) {
		return state == OperatorFinalStates.CORRECT_BLOCK_COMMENT_DELIMITER_FINALSTATE || state == OperatorFinalStates.CORRECT_COMMENT_DELIMITER_FINALSTATE; 
	}
	
	/**
	 * Obt�m o tipo do token.
	 * 
	 * @param state estado final do aut�mato
	 * @return tipo do token
	 */
	private String getTokenType(State state) {
		if(string.isFinalState()) {
			return "CADEIA_DE_CARACTERES";
		} else if(number.isFinalState()) {
			return "NUMERO";
		} else if(delimiter.isFinalState()) {
			return "DELIMITADOR";
		} else if(identifier.isFinalState()) {
			if(state == IdentifierFinalStates.CORRECTIDENTIFIER_FINALSTATE) {
				return "IDENTIFICADOR";
			} else {
				return "PALAVRA_RESERVADA";
			}
		} else {
			if(state == OperatorFinalStates.CORRECT_OPERATOR_ARITHMETIC_FINALSTATE) {
				return "OPERADOR_ARITMETICO";
			} else if(state == OperatorFinalStates.CORRECT_OPERATOR_LOGIC_FINALSTATE) {
				return "OPERADOR_LOGICO";
			} else  if (state == OperatorFinalStates.CORRECT_OPERATOR_RELATIONAL_FINALSTATE){
				return "OPERADOR_RELACIONAL";
			} else if(state == OperatorFinalStates.CORRECT_BLOCK_COMMENT_DELIMITER_FINALSTATE){
				return "COMENTARIO DE BLOCO";
			} else {
				return "COMENTARIO DE LINHA";
			}
		}
	}
	
	/**
	 * Obt�m uma String contendo os resultados da an�lise l�xica
	 * 
	 * @return string com os resultados da an�lise l�xica
	 */
	public String getResults() {
		String result = "TOKENS:" + System.lineSeparator() + "Linha / Nome do Token / Valor" + System.lineSeparator() + System.lineSeparator();
		
		Iterator<Token> it = tokens.iterator();
		
		while(it.hasNext()) {
			Token token = it.next();
			result = result + token.toString();
		}
		
		if(errors.size() == 0) {
			result = result + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + "SUCESSO!";
		} else {
			result = result + System.lineSeparator() + System.lineSeparator() + System.lineSeparator() + "ERROS:" + System.lineSeparator() + System.lineSeparator();
			Iterator<Error> ie = errors.iterator();
			
			while(ie.hasNext()) {
				System.lineSeparator();
				Error error = ie.next();
				result = result + error.toString();
			}
		}
		
		return result;
	}
	
	/**
	 * Obtém os tokens gerados na análise léxica.
	 * 
	 * @return lista de tokens gerada na análise léxica
	 */
	public List<Token> getTokens() {
		return tokens;
	}

}
