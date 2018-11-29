/**
 * 
 */
package model;


/**
 * Representa um token no contexto do analisador léxico.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Token {
	
	/** Nome do léxico */
	private String name;
	
	/** Valor (lexema) do token */
	private String value;
	
	/** Linha em que encontra-se o token */
	private int row;
	
	/**
	 * Obtém uma instância do token
	 * 
	 * @param name nome do token
	 * @param value valor (lexema) do token
	 * @param row linha em que encontra-se o token
	 */
	public Token(String name, String value, int row) {
		this.name = name;
		this.value = value;
		this.row = row;
	}
	
	/**
	 * Obtém uma string com os atributos da classe.
	 */
	public String toString() {
		return row + "  " + name + "	" + value + System.lineSeparator();
	}
	
	public String getValue() {
		return value;
	}
	
	public String getTokenClass() {
		return name;
	}
	
	public int getRow() {
		return row;
	}
	
}
