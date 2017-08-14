package br.com.dsls.useCaseDiagram;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Leandro Luque
 */
public class Teste {

    public static void main(String[] args) throws Exception {
        // create a CharStream that reads from standard input
        String filePath = "src\\scripts\\diagrama.ucd";
        Reader reader = null;
        
        String texto = "Diagrama \"Diagrama 1\"\n" +
"    Atore\n" +
"        \"Ator 1\",\n" +
"        \"Ator 2\" inherits from \"Ator 1\".\n" +
"\n" +
"    Caso de Uso\n" +
"        \"Caso de uso 1\",\n" +
"        \"Caso de uso 2\",\n" +
"        \"Caso de uso 3\".\n" +
"\n" +
"    Relaçõe \n" +
"        \"Ator 1\" associates with \"Caso de uso 1\",\n" +
"        \"Caso de uso 1\" include \"Caso de uso 2\",\n" +
"        \"Caso de uso 3\" extend \"Caso de uso 1\".\n";
        
        

//        try {
//            reader = new FileReader(filePath);
//        } catch (FileNotFoundException e) {
//            System.out.println(e);
//            System.err.println("Error: Unable to find source code file at " + filePath);
//            return;
//        }
        ANTLRInputStream input = new ANTLRInputStream(texto);

        // create a lexer that feeds off of input CharStream
        UseCaseDiagramLexer lexer = new UseCaseDiagramLexer(input);

        // create a buffer of tokens pulled from the lexer
        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // create a parser that feeds off the tokens buffer
        UseCaseDiagramParser parser = new UseCaseDiagramParser(tokens);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        MyErrorListener eListener = new MyErrorListener();
        parser.addErrorListener(eListener); // add ours

        ParseTree tree = parser.useCaseDiagram(); // begin parsing at init rule

        //System.out.println(tree.toStringTree(parser)); // print LISP-style tree
        ParseTreeWalker walker = new ParseTreeWalker();
        
        DSLToJavaObject ok = new DSLToJavaObject();
        walker.walk(ok, tree);
//        System.out.println(ok.getDiagram().toString());
        
        DSLToXML xml = new DSLToXML();
        walker.walk(xml, tree);
        
        System.out.println(eListener.getErrors().toString());
        //System.out.println(xml.getXML());
    }
}
