package br.com.dsls.useCaseDiagram_v2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;

/**
 *
 * @author Leandro Luque
 */
public class UseCaseDiagramErrorListener extends BaseErrorListener {
    
    private int numberOfErrors;
    private final List<String> errors;

    public UseCaseDiagramErrorListener() {
        this.numberOfErrors = 0;
        this.errors = new ArrayList<>();
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer,
            Object offendingSymbol,
            int line, int charPositionInLine,
            String msg,
            RecognitionException e) {

        if (recognizer instanceof Parser) {
            List<String> stack = ((Parser) recognizer).getRuleInvocationStack();
            Collections.reverse(stack);
            System.out.println("PARSER - ERROR STACK: " + stack);
        } else if(recognizer instanceof Lexer) {
            System.out.println("LEXER - TOKEN: " +((Lexer)recognizer).getToken());
        }

        System.out.println("OFFENDING SYMBOL: " + offendingSymbol);
        System.out.println("LINHA: " + line + " POSIÇÃO: " + charPositionInLine);
        System.out.println("ERROR MSG: " + msg);
        System.out.println("ERROR: " + e + " MESSAGE: " + (e != null ? e.getMessage() : "NULL"));
        
        numberOfErrors++;
        errors.add(msg.concat(" Line: "+line+", "+charPositionInLine));
    }

    public int getNumberOfErrors() {
        return numberOfErrors;
    }
    
    public List<String> getErrors() {
        return errors;
    }

}
