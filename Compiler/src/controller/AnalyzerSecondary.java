package controller;

import java.util.LinkedList;

import model.First;
import model.Token;
import model.TokensFlow;
import model.Util;

public class AnalyzerSecondary {
	
	//<More Classes> ::= <Class Declaration><More Classes> | <>
	public static void analiseMoreClasses() {
		Analyzer.analiseClassDeclaration();
		if(TokensFlow.hasNext() && First.check("MoreClasses", TokensFlow.getToken())) {
			analiseMoreClasses();
			return;
		} else {
			return;
		}
		
	}
	
	//<Class Identification> ::= Identifier <Class Heritage> '{' <Class Body> '}'
	public static void analiseClassIdentification() {
		Util.handleTerminal("IDENTIFICADOR", false, false);
		

		if(TokensFlow.hasNext() && First.check("ClassHeritage", TokensFlow.getToken())) {
			analiseClassHeritage();
			Util.handleTerminal("{", true, false);
			analiseClassBody();
			Util.handleTerminal("}", true, false);
			return;
 		} else {			
			Util.handleTerminal("{", true, false);
			analiseClassBody();
			Util.handleTerminal("}", true, false);
			
			return;
		}
	}

	//<Class Heritage> ::= 'extends' Identifier | <>
	public static void analiseClassHeritage() {
		Util.handleTerminal("extends", true, false);
		Util.handleTerminal("IDENTIFICADOR", false, false);	
		return;
	}
	
	//<Class Body> ::= <Class Attributes> <Class Methods>
	public static void analiseClassBody() {
		analiseClassAttributes();
		if(TokensFlow.hasNext() && First.check("ClassMethods", TokensFlow.getToken())) {
			analiseClassMethods();
			return;
		} else {
			return;
		}
	}
	
	//<Class Attributes> ::= <Variable Declaration> 
	public static void analiseClassAttributes() {
		if(TokensFlow.hasNext() && First.check("VariableDeclaration", TokensFlow.getToken())) {
			Analyzer.analiseVariableDeclaration();
			return;
		} else {
			return;
		}
	}
	
	//<Class Methods> ::= <Method Declaration> | <>
	public static void analiseClassMethods() {
		Analyzer.analiseMethodDeclaration();
		return;
	}
		
	//<Constants> ::= Type <ConstAttribution> <More Constants> 
	public static void analiseConstants() {
		Util.handleTerminal(null, false, true);
		analiseConstAttribution();
		analiseMoreConstants();
		return;
	}
	
	//<More Constants> ::= ',' <ConstAttribution> <More Constants> | ';' <New Declaration> 
	public static void analiseMoreConstants() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			analiseConstAttribution();
			analiseMoreConstants();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("NewDeclaration", TokensFlow.getToken())) {
				analiseNewDeclaration();
				return;
			} else {
				return;
			}
		}  else {
			Util.addError(First.MoreConstants.toString());
		}
	}
	
	//<New Declaration> ::= <Constants> | <>
	public static void analiseNewDeclaration() {
		analiseConstants();
		return;
	}

	
	//<ConstAttribution> ::= Identifier '=' <Value>
	public static void analiseConstAttribution() {
		Util.handleTerminal("IDENTIFICADOR", false, false);
		Util.handleTerminal("=", true, false);
		analiseValue();
		return;

	}
	
	//<Value> ::= Number | 'true' | 'false' | CadeCharacters
	public static void analiseValue() {
		if(TokensFlow.hasNext() && (TokensFlow.getToken().getTokenClass().equals("NUMERO") 
				|| TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES") 
				|| TokensFlow.getToken().getValue().equals("true")
				|| TokensFlow.getToken().getValue().equals("false")
				)) {
			
			TokensFlow.next();
			return;
		} else {
			Util.addError(First.Value.toString());
		}
	}
	
	//<Variable> ::= Type <Variable2> | Identifier <Variable2>
	public static void analiseVariable() {
		if(TokensFlow.hasNext() && Util.isType(TokensFlow.getToken())) {
			TokensFlow.next();
			analiseVariable2();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			analiseVariable2();
			return;
		} else if(TokensFlow.hasNext()) {
			Util.addError(First.VariableDeclaration.toString());
		}
	}
	
	//<Variable2> ::= <Name> <More Variables>
	public static void analiseVariable2() {
		analiseName();
		
		if(TokensFlow.hasNext() && First.check("MoreVariables", TokensFlow.getToken())) {
			analiseMoreVariables();
			return;
		} else {
			return;
		}
	}
	
	//<More Variables> ::= <Variable> | <>
	public static void analiseMoreVariables() {
		analiseVariable();
	}
	
	//<Name> ::= Identifier<Array Verification><More Names>
	public static void analiseName() {
		Util.handleTerminal("IDENTIFICADOR", false, false);
			
		if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
			analiseArrayVerification();
			analiseMoreNames();
			return;

		} else {
			analiseMoreNames();
			return;
		}
	}
	
	//<More Names> ::= ',' <Name> | ';'
	public static void analiseMoreNames() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(",")) {
			TokensFlow.next();
			analiseName();
			return;
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals(";")) {
			TokensFlow.next();
			return;
		} else {
			Util.addError(First.MoreNames.toString());
		}
	
	}
	
	//<More Methods> ::= <Method Declaration> | <>      
	public static void analiseMoreMethods() {
		Analyzer.analiseMethodDeclaration();
		return;
	}
	
	//<Parameter Declaration> ::= <Parameter Declaration2> | <>
	public static void analiseParameterDeclaration() {
		analiseParameterDeclaration2();
		return;
	}
	
	//<Parameter Declaration2> ::= <Type> Identifier <Array Verification> <More Parameters>
	public static void analiseParameterDeclaration2() {
		if(TokensFlow.hasNext()) {
			analiseType();
			Util.handleTerminal("IDENTIFICADOR", false, false);		
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				analiseArrayVerification();
				if(TokensFlow.hasNext() && First.check("MoreParameters", TokensFlow.getToken())) {
					analiseMoreParameters();
					return;
				} else {
					return;
				}
						
			} else {
				if(TokensFlow.hasNext() && First.check("MoreParameters", TokensFlow.getToken())) {
					analiseMoreParameters();
					return;
						
				} else {
					return;
				}
			}
		}
	}
	
	//<Type> ::= Type | Identifier 
	public static void analiseType() {
		if(TokensFlow.hasNext() && Util.isType(TokensFlow.getToken())) {
			TokensFlow.next();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			return;
		} else {
			Util.addError(First.Type.toString());
		}
	}
	
	//<More Parameters> ::= ',' <Parameter Declaration2> | <>
	public static void analiseMoreParameters() {
		Util.handleTerminal(",", true, false);
		analiseParameterDeclaration2();
		return;
	}
	
	//<Array Verification> ::= '['<Array Index>']'<DoubleArray> | <>
	public static void analiseArrayVerification() {
		Util.handleTerminal("[", true, false);	
		analiseArrayIndex();
		Util.handleTerminal("]", true, false);
		if(TokensFlow.hasNext() && First.check("DoubleArray", TokensFlow.getToken())) {
			analiseDoubleArray();
			return;
		} else {
			return;
		}
	}	
	
	//<DoubleArray> ::= '['<Array Index>']' | <>
	public static void analiseDoubleArray() {
		Util.handleTerminal("[", true, false);
		analiseArrayIndex();
		Util.handleTerminal("]", true, false);
		return;
	}
	
	//<Array Index> ::= <Add Exp>
	public static void analiseArrayIndex() {
		analiseAddExp();
		return;
	}
	
	//<Return> ::= 'return' <Return1> 
	public static void analiseReturn() {
		Util.handleTerminal("return", true, false);
		analiseReturn1();
		return;
	}
	
	//<Return1> ::= Identifier <Array Verification> | <Value>
	public static void analiseReturn1() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				analiseArrayVerification();
				return;
			} else {
				return;
			}
		} else {
			if(TokensFlow.hasNext() && First.check("Value", TokensFlow.getToken())) { 
				analiseValue();
				return;
			} else {
				Util.addError(First.Return1.toString());
			}
		}
	}
	
	//<Else Statement> ::= 'else''{'<Commands>'}' | <>
	public static void analiseElseStatement() {
		Util.handleTerminal("else", true, false);
		Util.handleTerminal("{", true, false);
		
		if(TokensFlow.hasNext() && First.check("Commands", TokensFlow.getToken())) { 
			Analyzer.analiseCommands();
			Util.handleTerminal("}", true, false);
			return;
		} else {
			Util.handleTerminal("}", true, false);
			return;
		}
	}
	
	//<Verif> ::= <Normal Attribution2> | <Complement>
	public static void analiseVerif() {
		if(TokensFlow.hasNext() && First.check("NormalAttribution2", TokensFlow.getToken())) { 
			analiseNormalAttribution2();
			return;
		} else if(TokensFlow.hasNext() && First.check("Complement", TokensFlow.getToken())) {
			analiseComplement();
			return;
		} else {
			Util.addError(First.Verif.toString());
		}
	}
	
	//<Increment> ::= '++' | '--'
	public static void analiseIncrement() {
		if(TokensFlow.hasNext()) {
			if(TokensFlow.getToken().getValue() == "++" || TokensFlow.getToken().getValue() == "--") {
				TokensFlow.next();
				return;
			} else {
				Util.addError(First.Increment.toString());
			}
		}
	}
	
	//<Normal Attribution2> ::= '=' <Normal Attribution3> | <Increment>  
	public static void analiseNormalAttribution2() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("=")) {
			TokensFlow.next();
			analiseNormalAttribution3();
			return;
		} else if(TokensFlow.hasNext() && First.check("Increment", TokensFlow.getToken())) {
			analiseIncrement();
			return;
		} else {
			Util.addError(First.NormalAttribution2.toString());
		}
	}
	
	//<Normal Attribution3> ::= <Expression> | CadeCaracters
	public static void analiseNormalAttribution3() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();
			return;
		} else if(TokensFlow.hasNext() && First.check("Expression", TokensFlow.getToken())){
			Analyzer.analiseExpression();
			return;
		} else {
			Util.addError(First.NormalAttribution3.toString());
		}
	}
	
	//<Relational Exp> ::= RelationalOperator <Add Exp> <Logical Exp> | <Logical Exp>
	public static void analiseRelationalExp() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("OPERADOR_RELACIONAL")) {
			TokensFlow.next();
			analiseAddExp(); 
			
			if(TokensFlow.hasNext() && First.check("LogicalExp", TokensFlow.getToken())) {
				analiseLogicalExp();
				return;
			} else {
				return;
			}
			
		} else {
			if(TokensFlow.hasNext() && First.check("LogicalExp", TokensFlow.getToken())) {
				analiseLogicalExp();
				return;
			} else {
				return;
			}
		}
	}
	
	//<Logical Exp> ::= '||' <Expression> | '&&' <Expression> | <> 
	public static void analiseLogicalExp() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("||")) { 
			TokensFlow.next();
			Analyzer.analiseExpression();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("&&")) { 
			TokensFlow.next();
			Analyzer.analiseExpression();
			return;
		} else {
			Util.addError(First.LogicalExp.toString());
		}
	}
	
	//<Add Exp> ::= <Mult Exp> <D>
	public static void analiseAddExp() {
		analiseMultExp();
		if(TokensFlow.hasNext() && First.check("D", TokensFlow.getToken())) {
			analiseD();
			return;
		} else {
			return;
		}
	}	
	

	//<D> ::= '+' <Add Exp> | '-' <Add Exp> | <>
	public static void analiseD() { 
		if(TokensFlow.hasNext() && (TokensFlow.getToken().getValue().equals("+") || TokensFlow.getToken().getValue().equals("-"))) {
			TokensFlow.next();
			analiseAddExp();
			return;
		} else {
			Util.addError(First.D.toString());
		}
	}
	
	// <Mult Exp> ::= <Neg Exp> <E>
	public static void analiseMultExp() {
		analiseNegExp();
		if(TokensFlow.hasNext() && First.check("E", TokensFlow.getToken())) {
			analiseE();
			return;
		} else {
			return;
		}
	}
	
	//<E> ::= '*' <Mult Exp> | '/' <Mult Exp> | <>
	public static void analiseE() { 
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("*")) {
			TokensFlow.next();
			analiseMultExp();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("/")) {
			TokensFlow.next();
			analiseMultExp();
			return;
		} else {
			Util.addError(First.E.toString());
		}
	}
	
	//<Neg Exp>  ::= '-' <Exp Value> |  <Exp Value> <G> | '!' <Exp Value> | '++' <Exp Value> | '--'<Exp Value>
	public static void analiseNegExp() { 
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("-")) { //'-' <F>
			TokensFlow.next();
			analiseExpValue();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("!")) {
			TokensFlow.next();
			analiseExpValue();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("++")) {
			TokensFlow.next();
			analiseExpValue();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("--")) {
			TokensFlow.next();
			analiseExpValue();
			return;
		} else if(TokensFlow.hasNext() && First.check("ExpValue", TokensFlow.getToken())) {
				analiseExpValue();
				if(TokensFlow.hasNext() && First.check("G", TokensFlow.getToken())) {
					analiseG();
					return;
				} else {
					return;
				}
		} else {
			Util.addError(First.NegExp.toString());
		}
	}
	
	//<G> ::= '--' | '++' | <>
	public static void analiseG() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("--")) {
			TokensFlow.next();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("++")) {
			TokensFlow.next();
			return;
		} else {
			Util.addError(First.G.toString());
		}
	}
	
	////<Exp Value> ::= Number |  '(' <Expression> ')' |  Identifier<Array Verification><Attr><Param2> | 'true' | 'false' 
	public static void analiseExpValue() { 
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("NUMERO")) {
			TokensFlow.next();
			return;
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getValue().equals("(")) {
			TokensFlow.next();
			if(TokensFlow.hasNext() && First.check("Expression", TokensFlow.getToken())){
				Analyzer.analiseExpression();
				Util.handleTerminal(")", true, false);
				return;
			}
			
		} else if(TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) { 
			TokensFlow.next();
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				analiseArrayVerification();		
				
				if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
					analiseAttr();
					if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
						analiseParam2();
						return;
					} else {
						return;
					} 
					
				} else if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
					analiseParam2();
					return;
				} else {
					return;
				} 
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				analiseAttr();
				if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
					analiseParam2();
					return;
				} else {
					return;
				}
					
			} else if(TokensFlow.hasNext() && First.check("Param2", TokensFlow.getToken())) {
				analiseParam2();
				return;
			} else {
				return;
			}
			
		} else if(TokensFlow.hasNext() && (TokensFlow.getToken().getValue().equals("true") || TokensFlow.getToken().getValue().equals("false"))) {
			TokensFlow.next();
			return;
			
		} else {
			Util.addError(First.ExpValue.toString());
		}
	}	
	
	//<Param2>::= <Complement> | <>
	public static void analiseParam2() {
		analiseComplement();
		return;
	}
	
	//<Complement> ::= '('<Param>')'
	public static void analiseComplement() {
		Util.handleTerminal("(", true, false);
		if(TokensFlow.hasNext() && First.check("Param", TokensFlow.getToken())) {		
			analiseParam();
			Util.handleTerminal(")", true, false);
			return;
		} else {
			Util.handleTerminal(")", true, false);
			return;
		}
	}
	

	//<Param> ::= <> | <Expression><MoreParam> | CadeCaracters<MoreParam> 
	public static void analiseParam() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();			
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				analiseMoreParam();
				return;
			} else {
				return;
			}			
		} else if(TokensFlow.hasNext() && First.check("Expression", TokensFlow.getToken())) {
			Analyzer.analiseExpression();			
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				analiseMoreParam();
				return;
			} else {
				return;
			}
		} else {
			Util.addError(First.Param.toString());
		}
	}
	
	
	//<MoreParam> ::= ','<ObrigatoryParam> | <>
	public static void analiseMoreParam() {
		Util.handleTerminal(",", true, false);
			analiseObrigatoryParam();
			return;
	}
	
	//<ObrigatoryParam> ::= <Expression><MoreParam> | CadeCaracters<MoreParam>
	public static void analiseObrigatoryParam() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();			
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				analiseMoreParam();
				return;
			} else {
				return;
			}
		} else if(TokensFlow.hasNext() && First.check("Expression", TokensFlow.getToken())) {
			Analyzer.analiseExpression();		
			if(TokensFlow.hasNext() && First.check("MoreParam", TokensFlow.getToken())) {
				analiseMoreParam();
				return;
			} else {
				return;
			}
		} else {
			Util.addError(First.ObrigatoryParam.toString());
		}
	}
	
	//<Reading_1> ::= Identifier<Array Verification><Attr><More Readings> 
	public static void analiseReading1() {
		Util.handleTerminal("IDENTFICADOR", false, false);
			
		if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
			analiseArrayVerification();	
			
			if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				analiseAttr();
				if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
					analiseMoreReadings();
					return;
				} else {
					return;
				}
						
			} else if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
				analiseMoreReadings();
				return;
			} else {
				return;
			}
				
		} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
			analiseAttr();
			if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
				analiseMoreReadings();
				return;
			} else {
				return;
			}
		} else if(TokensFlow.hasNext() && First.check("MoreReadings", TokensFlow.getToken())) {
			analiseMoreReadings();
			return;
		} else {
			return;
		}
	}

	//<More Readings> ::= ',' <Reading_1> | <>
	public static void analiseMoreReadings() {
		Util.handleTerminal(",", true, false);
		analiseReading1();
		return;
	}
	
	//<Writing_1> ::= Identifier<Array Verification><Attr><More Writings> | CadeCaracters<More Writings>                    
	public static void analiseWriting1() {
		if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("IDENTIFICADOR")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
				analiseArrayVerification();
					if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
						analiseAttr();
							if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
								analiseMoreWritings();
								return;
							} else {
								return;
							}
							
					} else if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
						analiseMoreWritings();
						return;
					} else {
						return;
					}
			} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				analiseAttr();
				if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
					analiseMoreWritings();
					return;
				} else {
					return;
				}
			} else if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
				analiseMoreWritings();
				return;
			} else {
				return;
			}
			
		} else if(TokensFlow.hasNext() && TokensFlow.getToken().getTokenClass().equals("CADEIA_DE_CARACTERES")) {
			TokensFlow.next();
			
			if(TokensFlow.hasNext() && First.check("MoreWritings", TokensFlow.getToken())) {
				analiseMoreWritings();
				return;
			} else {
				return;
			}
			
		} else {
			Util.addError(First.Writing1.toString());
		}
	}
	
	//<More Writings> ::= ',' <Writing_1> | <>
	public static void analiseMoreWritings() {
		Util.handleTerminal(",", true, false);
		analiseWriting1();
		return;
	}
	
	//<Attr> ::= '.'Identifier<Array Verification><Attr> | <>
	public static void analiseAttr() {
		Util.handleTerminal(".", true, false);
		Util.handleTerminal("IDENTIFICADOR", false, false);
		
		if(TokensFlow.hasNext() && First.check("ArrayVerification", TokensFlow.getToken())) {
			analiseArrayVerification(); 
			if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
				analiseAttr();
				return;
			} else {
				return;
			}
			
		} else if(TokensFlow.hasNext() && First.check("Attr", TokensFlow.getToken())) {
			analiseAttr();
			return;
		} else {
			return;
		}
	}
	
}