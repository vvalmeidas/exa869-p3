/**
 * 
 */
package model;

import java.util.LinkedList;

/**
 *
 * @author Nadine Cerqueira
 * @author Valmir Vinicius
 *
 */
public class First {
	
	public static LinkedList<String> Else = new LinkedList<String>() {{
		add("else");
	}};
	
	public static LinkedList<String> Commands = new LinkedList<String>() {{
		add("--");
		add("++");
		add("if");
		add("read");
		add("return");
		add("while");
		add("write");
		add("IDENTIFICADOR");
	}};
	
	public static LinkedList<String> Expression = new LinkedList<String>() {{
		add("-");
		add("--");
		add("!");
		add("(");
		add("++");
		add("false");
		add("true");
		add("IDENTIFICADOR");
		add("NUMERO");
	}};
	
	public static LinkedList<String> A = new LinkedList<String>() {{
		add("||");
	}};
	
	public static LinkedList<String> AddExp = new LinkedList<String>() {{
		add("-");
		add("--");
		add("!");
		add("(");
		add("++");
		add("false");
		add("true");
		add("IDENTIFICADOR");
		add("NUMERO");
	}};
	
	public static LinkedList<String> B = new LinkedList<String>() {{
		add("&&");
	}};
	
	public static LinkedList<String> C = new LinkedList<String>() {{
		add("OPERADOR_RELACIONAL");
	}};
	
	public static LinkedList<String> D = new LinkedList<String>() {{
		add("-");
		add("+");
	}};
	
	public static LinkedList<String> E = new LinkedList<String>() {{
		add("*");
		add("/");
	}};
	
	public static LinkedList<String> G = new LinkedList<String>() {{
		add("--");
		add("++");
	}};
	
	public static LinkedList<String> H = new LinkedList<String>() {{
		add("!");
	}};
	
	public static LinkedList<String> RelationalExp = new LinkedList<String>() {{
		add("&&");
		add("||");
		add("==");
		add(">");
		add("<");
		add(">=");
		add("<=");
		add("!=");
	}};
	
	public static LinkedList<String> LogicalExp = new LinkedList<String>() {{
		add("&&");
		add("||");
	}};
	
	public static LinkedList<String> DoubleArray = new LinkedList<String>() {{
		add("[");
	}};
	
	public static LinkedList<String> ArrayVerification = new LinkedList<String>() {{
		add("[");
	}};
	
	public static LinkedList<String> Attr = new LinkedList<String>() {{
		add(".");
	}};
	
	public static LinkedList<String> MoreReadings = new LinkedList<String>() {{
		add(",");
	}};
	
	public static LinkedList<String> MoreParam = new LinkedList<String>() {{
		add(",");
	}};
	
	public static LinkedList<String> Param = new LinkedList<String>() {{
		add("-");
		add("--");
		add("!");
		add("(");
		add("++");
		add("false");
		add("true");
		add("CADEIA_DE_CARACTERES");
		add("IDENTIFICADOR");
		add("NUMERO");
	}};
	
	public static LinkedList<String> MoreWritings = new LinkedList<String>() {{
		add(",");
	}};
	
	public static LinkedList<String> MoreVariables = new LinkedList<String>() {{
		add("IDENTIFICADOR");
		add("int");
		add("float");
		add("bool");
		add("string");
		add("void");
	}};
	
	public static LinkedList<String> MoreConstants = new LinkedList<String>() {{
		add(",");
		add(";");
		add("int");
		add("float");
		add("bool");
		add("string");
		add("void");
	}};
	
	public static LinkedList<String> VariableDeclaration = new LinkedList<String>() {{
		add("variables");
	}};	
	
	public static LinkedList<String> ClassMethods = new LinkedList<String>() {{
		add("method");
	}};
	
	public static LinkedList<String> ClassHeritage = new LinkedList<String>() {{
		add("extends");
	}};
	
	public static LinkedList<String> ConstantDeclaration = new LinkedList<String>() {{
		add("const");
	}};
	
	public static LinkedList<String> MoreClasses = new LinkedList<String>() {{
		add("class");
	}};
	
	public static boolean check(String production, Token token) {
		if(production == "Else") {
			return Else.contains(token.getValue()) || Else.contains(token.getTokenClass()); 
		} else if(production == "Commands") {
			return Commands.contains(token.getValue()) || Commands.contains(token.getTokenClass()); 
		} else if(production == "Expression") {
			return Expression.contains(token.getValue()) || Expression.contains(token.getTokenClass()); 
		} else if(production == "A") {
			return A.contains(token.getValue()) || A.contains(token.getTokenClass()); 
		} else if(production == "AddExp") {
			return AddExp.contains(token.getValue()) || AddExp.contains(token.getTokenClass()); 
		} else if(production == "B") {
			return B.contains(token.getValue()) || B.contains(token.getTokenClass()); 
		} else if(production == "C") {
			return C.contains(token.getValue()) || C.contains(token.getTokenClass()); 
		} else if(production == "D") {
			return D.contains(token.getValue()) || D.contains(token.getTokenClass()); 
		} else if(production == "E") {
			return E.contains(token.getValue()) || E.contains(token.getTokenClass()); 
		} else if(production == "G") {
			return G.contains(token.getValue()) || G.contains(token.getTokenClass()); 
		} else if(production == "H") {
			return H.contains(token.getValue()) || H.contains(token.getTokenClass()); 
		} else if(production == "RelationalExp") {
			return RelationalExp.contains(token.getValue()) || RelationalExp.contains(token.getTokenClass()); 
		} else if(production == "LogicalExp") {
			return LogicalExp.contains(token.getValue()) || LogicalExp.contains(token.getTokenClass()); 
		} else if(production == "DoubleArray") {
			return DoubleArray.contains(token.getValue()) || DoubleArray.contains(token.getTokenClass()); 
		} else if(production == "ArrayVerification") {
			return ArrayVerification.contains(token.getValue()) || ArrayVerification.contains(token.getTokenClass()); 
		} else if(production == "Attr") {
			return Attr.contains(token.getValue()) || Attr.contains(token.getTokenClass()); 
		} else if(production == "MoreReadings") {
			return MoreReadings.contains(token.getValue()) || MoreReadings.contains(token.getTokenClass()); 
		} else if(production == "MoreParam") {
			return MoreParam.contains(token.getValue()) || MoreParam.contains(token.getTokenClass()); 
		} else if(production == "Param") {
			return Param.contains(token.getValue()) || Param.contains(token.getTokenClass()); 
		} else if(production == "MoreWritings") {
			return MoreWritings.contains(token.getValue()) || MoreWritings.contains(token.getTokenClass()); 
		} else if(production == "MoreVariables") {	
			return MoreVariables.contains(token.getValue().toLowerCase()) || MoreVariables.contains(token.getTokenClass()); 
		} else if(production == "MoreConstants") {	
			return MoreConstants.contains(token.getValue()) || MoreConstants.contains(token.getTokenClass()); 
		} else if(production == "VariableDeclaration") {	
			return VariableDeclaration.contains(token.getValue()) || VariableDeclaration.contains(token.getTokenClass()); 
		} else if(production == "ClassMethods") {	
			return ClassMethods.contains(token.getValue()) || ClassMethods.contains(token.getTokenClass()); 
		} else if(production == "ClassHeritage") {	
			return ClassHeritage.contains(token.getValue()) || ClassHeritage.contains(token.getTokenClass()); 
		} else if(production == "ConstantDeclaration") {	
			return ConstantDeclaration.contains(token.getValue()) || ConstantDeclaration.contains(token.getTokenClass()); 
		}else if(production == "MoreClasses") {	
			return MoreClasses.contains(token.getValue()) || MoreClasses.contains(token.getTokenClass()); 
		}
		
		return false;
	}
	
}
