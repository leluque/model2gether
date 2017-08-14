
package br.com.test;

import br.com.dsls.useCaseDiagram_v2.DSLToJavaObject;
import br.com.dsls.useCaseDiagram_v2.UseCaseDiagramErrorListener;
import br.com.dsls.useCaseDiagram_v2.UseCaseDiagramErrorStrategy;
import br.com.dsls.useCaseDiagram_v2.UseCaseDiagramLexer;
import br.com.dsls.useCaseDiagram_v2.UseCaseDiagramParser;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Leandro Luque
 */
public class TesteANTLR {
    
    public static void main(String[] args) {
        
        ANTLRInputStream input = new ANTLRInputStream(getDiagram());
        
        UseCaseDiagramLexer lexer = new UseCaseDiagramLexer(input);
        
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        
        UseCaseDiagramErrorListener eListener = new UseCaseDiagramErrorListener();
        
        UseCaseDiagramParser parser = new UseCaseDiagramParser(tokens);
        
        lexer.removeErrorListeners();
        lexer.addErrorListener(eListener);
        parser.removeErrorListeners(); // remove ConsoleErrorListener
        parser.addErrorListener(eListener); // add ours
        System.out.println("ERROS ANTES: " + parser.getNumberOfSyntaxErrors());
//        parser.setErrorHandler(new UseCaseDiagramErrorStrategy());
        System.out.println(getDiagram());
        parser.useCaseDiagram();
        System.out.println("ERROS DEPOIS: " + eListener.getNumberOfErrors());
//        ParseTreeWalker walker = new ParseTreeWalker();
//        DSLToJavaObject obj = new DSLToJavaObject(parser);
//        walker.walk(obj, tree);
        
//        System.out.println("DIAGRAMA:\n" + obj.getDiagram());
        
//        ParseTree tree = parser.useCaseDiagram(); // begin parsing at init rule
//        
//        ParseTreeWalker walker = new ParseTreeWalker();
    }
    
    public static String getDiagram() {
        return 
            "Diagrama \"Clínica Médica\"\n" +
            "  Atores\n" +
            "    \"Paciente,\n" +
            "    \"Secretaria\"\n" +
            "    \"Doutor\".\n" +
            "  Casos de Uso\n" +
            "    \"Cancelar Consulta\",\n" +
            "    \"Marcar Consulta\",\n" +
            "    \"Procurar Registro do Paciente\",\n" +
            "    \"Pedir Remédio\".\n" +
            "  Relações\n" +
            "    \"Paciente\" conecta com \"Cancelar Consulta\",\n" +
            "    \"Paciente\" conecta com \"Marcar Consulta\",\n" +
            "    \"Paciente\" conecta com \"Pedir Remédio\",\n" +
            "    \"Secretaria\" conecta com \"Cancelar Consulta\",\n" +
            "    \"Secretaria\" conecta com \"Marcar Consulta\",\n" +
            "    \"Doutor\" conecta com \"Pedir Remédio\",\n" +
            "    \"Marcar Consulta\" include \"Procurar Registro do Paciente\",\n" +
            "    \"Pedir Remédio\" include \"Procurar Registro do Paciente\",\n" +
            "    \"Doutor\" herda de \"Secretaria\".";
    }
}
