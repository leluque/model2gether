package br.com.commands;

import br.com.dsls.useCaseDiagram_v2.UseCaseDiagramErrorListener;
import br.com.dsls.useCaseDiagram_v2.UseCaseDiagramLexer;
import br.com.dsls.useCaseDiagram_v2.UseCaseDiagramParser;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

public class ValidateTextualDiagram
        extends ICommand {

    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        String textDiagram = request.getParameter("diagram");

        ANTLRInputStream input = new ANTLRInputStream(textDiagram);
        UseCaseDiagramLexer lexer = new UseCaseDiagramLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        UseCaseDiagramErrorListener eListener = new UseCaseDiagramErrorListener();
        UseCaseDiagramParser parser = new UseCaseDiagramParser(tokens);

        lexer.removeErrorListeners();
        lexer.addErrorListener(eListener);
        parser.removeErrorListeners();
        parser.addErrorListener(eListener);
        parser.useCaseDiagram();

        response.getWriter().write(new Gson().toJson(eListener.getErrors()));

        return "";
    }
}
