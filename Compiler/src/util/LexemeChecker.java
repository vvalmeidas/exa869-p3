package util;
/**
 * 
 */

import java.util.ArrayList;

/**
 * Classe responsável por implementar métodos que realizam a checagem de um lexema.
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class LexemeChecker {
	
	private static String REGEX_DIGIT = "[\\d]";
	public static String REGEX_LETTER = "[a-zA-Z]";
	private static String REGEX_UNDERLINE = "_";
	private static String REGEX_LOGIC_OPERATOR = "!|&&|\\|\\|";
	private static String REGEX_ARITHMETIC_OPERATOR = "\\+|-|\\*|/|\\+\\+|--";
	public static String REGEX_RELATIONAL_OPERATOR = "!=|==|<|<=|>|>=|=";
	private static String REGEX_DELIMITER = "\\;|\\,|\\(|\\)|\\[|\\]|\\{|\\}|\\.";
	private static String REGEX_DELIMITERWITHOUTPOINT = "\\;|\\,|\\(|\\)|\\[|\\]|\\{|\\}";
	private static ArrayList<String> KEYWORDS = new ArrayList<String>() {{
	    add("class");
	    add("const");
	    add("variables");
	    add("method");
	    add("return");
	    add("main");
	    add("if");
	    add("then");
	    add("else");
	    add("while");
	    add("read");
	    add("write");
	    add("void");
	    add("int");
	    add("float");
	    add("bool");
	    add("string");
	    add("true");
	    add("false");
	    add("extends");
	}};
	
	
	/**
	 * Verifica se a entrada é um dígito válido.
	 * 
	 * @param character entrada a ser verificada
	 * @return true, se o a entrada for um dígito; falso, caso contrário
	 */
	public static boolean isDigit(char character) {
		String string = String.valueOf(character);
		return string.matches(REGEX_DIGIT);
	}
	
	/**
	 * Determina se o caractere lido é um delimitador para um número.
	 * 
	 * @param character entrada a ser verificada
	 * @return true, se o caractere for um delimitador para um número; falso, caso contrário
	 */
	public static boolean isNumberDelimiter(char character) {
		String string = String.valueOf(character);
		return character == '\n' || character == '\r' || character == ' '  || character == System.lineSeparator().charAt(0) || string.matches(REGEX_DELIMITERWITHOUTPOINT) || string.matches(REGEX_ARITHMETIC_OPERATOR) || string.matches(REGEX_RELATIONAL_OPERATOR) || string.matches(REGEX_LOGIC_OPERATOR);
	}

	/**
	 * Determina se o caractere lido é uma letra.
	 * 
	 * @param character entrada a ser verificada
	 * @return true se o caractere for uma letra
	 */
	public static boolean isLetter(char character) {
		String string = String.valueOf(character);
		return string.matches(REGEX_LETTER);
	}
	
	/**
	 * Determina se o caractere lido é um delimitador para identificadores.
	 * 
	 * @param character entrada a ser verificada
	 * @return true se o caractere for um delimitador para identificadores
	 */
	public static boolean isIdentifierDelimiter(char character) {
		String string = String.valueOf(character);
		return character == '\n' || character == '\r' || character == ' ' || character == '"' || string.matches(REGEX_DELIMITER) || string.matches(REGEX_RELATIONAL_OPERATOR) || string.matches(REGEX_LOGIC_OPERATOR) || string.matches(REGEX_DELIMITER) || string.matches(REGEX_ARITHMETIC_OPERATOR);
	}
	
	/**
	 * Determina se o caractere lido é um underline.
	 * 
	 * @param character entrada a ser verificada
	 * @return true se o caractere for um underline
	 */
	public static boolean isUnderline(char character) {
		String string = String.valueOf(character);
		return string.matches(REGEX_UNDERLINE);
	}
	
	/**
	 * Determina se o caractere lido é um operador artimético.
	 * 
	 * @param character entrada a ser verificada
	 * @return true, se o caractere for um operador arimético; falso, caso contrário
	 */
	public static boolean isArithmeticOperator(char character) {
		String string = String.valueOf(character);
		return string.matches(REGEX_ARITHMETIC_OPERATOR);
	}
	
	/**
	 * Determina se o caractere lido é um delimitador.
	 * 
	 * @param character entrada a ser verificada
	 * @return true, se o caractere for um delimitador; falso, caso contrário
	 */
	public static boolean isDelimiter(char character) {
		String string = String.valueOf(character);
		return string.matches(REGEX_DELIMITER);
	}
	
	/**
	 * Determina se o caractere lido é um operador lógico.
	 * 
	 * @param character entrada a ser verificada
	 * @return true, se o caractere for um operador lógico; falso, caso contrário
	 */
	public static boolean isLogicOperator(char character) {
		String string = String.valueOf(character);
		return string.matches(REGEX_LOGIC_OPERATOR);
	}

	/**
	 * Determina se o caractere lido é um operador relacional
	 * 
	 * @param character entrada a ser verificada
	 * @return true, se o caractere for um operador relacional; falso caso não seja.
	 */
	public static boolean isRelationalOperator(char character) {
		String string = String.valueOf(character);
		return string.matches(REGEX_RELATIONAL_OPERATOR);
	}
	
	/**
	 * Determina se o caractere lido é um símbolo válido.
	 * 
	 * @param character entrada a ser verificada
	 * @return true, se o caractere for um símbolo válido; falso caso não seja.
	 */
	public static boolean isValidSymbol(char character) {
		return (character >= 32 && character <= 126) && (character != 34);
	}
	
	/**
	 * Verifica se o caractere é válido para ser registrado como um token.
	 * 
	 * @param character entrada a ser verificada
	 * @return true, se o caractere for válido para token; falso, caso contrário
	 */
	public static boolean isValidForToken(char character) {
		return !(character == ' ' || character == 9 || character == '\r' || character == '\n');
	}
	
	/**
	 * Verifica se o lexema é uma palavra reservada.
	 * 
	 * @param string lexema a ser analisado
	 * @return true, se o lexema for uma palavra reservada; false, caso contrário
	 */
	public static boolean isKeyWord(String string) {
		return KEYWORDS.contains(string);
	}
	
	/**
	 * Verifica se o caractere é um espaço ou quebra de linha
	 * 
	 * @param character
	 * @return
	 */
	public static boolean isSpaceOrLineBreak(char character) {
		return character == 9 || character == 32 || character == 10 || character == 13 || character == System.lineSeparator().charAt(0);
	}
	
	/**
	 * Verifica se o caractere é um delimitador de cadeia de caracteres
	 * 
	 * @param character caractere a ser analisado
	 * @return true, se o caractere delimitador uma cadeia de caracteres; false, caso contrário
	 */
	public static boolean isStringDelimiter(char character) {
		return character == System.lineSeparator().charAt(0) || character == '\r' || character == '\n';
	}
	
	/**
	 * Verifica se o caractere é um espaço
	 * 
	 * @param character caractere a ser analisado
	 * @return true, se o caractere for um espaço; false, caso contrário
	 */
	public static boolean isSpace(char character) {
		return character == 9 || character == 32;
	}
}
