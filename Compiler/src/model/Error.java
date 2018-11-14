/**
 * 
 */
package model;

/**
 * Representa um erro no contexto do analisador l�xico.
 * 
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class Error {
	
	/** Descri��o do erro */
	private String description;
	
	/** Valor do lexema que causou o erro */
	private String value;
	
	/** Linha em que encontra-se o token */
	private int row;
	
	/**
	 * Obt�m uma inst�ncia do token
	 * 
	 * @param description descri��o do erro
	 * @param value valor do lexema que causou o erro
	 * @param row linha em que encontra-se o erro
	 */
	public Error(String description, String value, int row) {
		this.description = description;
		this.value = value;
		this.row = row;
	}
	
	/**
	 * Obt�m uma string com os atributos da classe.
	 */
	public String toString() {
		return description + " na linha " + row + ": '" + value + "'" + System.lineSeparator();
	}

}
