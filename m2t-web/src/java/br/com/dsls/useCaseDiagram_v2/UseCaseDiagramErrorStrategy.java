
package br.com.dsls.useCaseDiagram_v2;

import org.antlr.v4.runtime.DefaultErrorStrategy;
import org.antlr.v4.runtime.InputMismatchException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

/**
 *
 * @author Leandro Luque
 */
public class UseCaseDiagramErrorStrategy extends DefaultErrorStrategy {

    @Override
    public void recover(Parser recognizer, RecognitionException e) {
        System.out.println("PARSER:" + recognizer);
        System.out.println("EXCEPTION:" + e);
        
        throw new RuntimeException(e);
    }

    @Override
    public Token recoverInline(Parser recognizer) throws RecognitionException {
        System.out.println("PARSER INLINE:" + recognizer);
        
        throw new InputMismatchException(recognizer);
    }

    @Override
    public void sync(Parser recognizer) throws RecognitionException {
//        System.out.println("PARSER:" + recognizer.getCurrentToken());
        
    }
}
