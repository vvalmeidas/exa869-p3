import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import controller.FileController;
import model.Token;

public class Main {

	public static void main(String[] args) throws IOException {
		Map<String, String> sourceFiles = FileController.readFiles();
		Iterator<String> iSource = sourceFiles.keySet().iterator();
		List<Token> tokens = new LinkedList<Token>();
		
		while(iSource.hasNext()) {
			String fileName = iSource.next();
			String sourceCode = sourceFiles.get(fileName);
			
			Lexer lexer = new Lexer();
			lexer.initialize(sourceCode);
			tokens.addAll(lexer.getTokens());
			
			FileController.saveFile(fileName, lexer.getResults());
		}
	}

}
