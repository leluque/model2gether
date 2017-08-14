package br.com.commands;

import br.com.dao.UseCaseDiagramDao;
import br.com.luque.model2gether.SendMessage;
import br.com.models.UseCaseDiagram;
import br.com.models.User;
import br.com.util.Util;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoadDiagram
        extends ICommand {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        response.setContentType("text/html;charset=UTF-8");

        String idString = request.getParameter("id");
        String type = request.getParameter("type");
        String name = request.getParameter("name");
        String pagina = "";
        UseCaseDiagramDao dao = new UseCaseDiagramDao();
        UseCaseDiagram diagram = new UseCaseDiagram();
        int id = idString == null ? 0 : Integer.parseInt(idString);

        diagram.setId(id);

        if (id == 0) {
            diagram.setName(name);
            diagram = (UseCaseDiagram) dao.add(diagram);
            // Incluir diagrama na lista dos diagramas do usuario atual.
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            user.getMyDiagrams().add(diagram);
        } else {
            diagram = (UseCaseDiagram) dao.find(diagram);
        }
        switch (type) {
            case "grafico":
                pagina = "graphicalRep.jsp";
                break;
            case "textual":
                pagina = "textualRep.jsp";
        }

        request.setAttribute("id", diagram.getId());
        request.setAttribute("diagrama", Util.toStringJSONWithEscape(new Gson().toJson(diagram)));

        System.out.println("Carregando diagrama com id = " + diagram.getId());
        // Abre o diagrama e cadastra o usuário como peer.
        SendMessage.onOpen(request.getSession(), String.valueOf(id));

        return pagina;
    }
}
