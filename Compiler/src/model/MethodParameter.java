/**
 * 
 */
package model;

/**
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class MethodParameter {
	private String parameterType;
	private String parameterName;
	
	public MethodParameter(String parameterType, String parameterName) {
		this.parameterType = parameterType;
		this.parameterName = parameterName;
	}
	
	public String getParameterType() {
		return parameterType;
	}
	
	public String getParameterName() {
		return parameterName;
	}
}
