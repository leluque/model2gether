package br.com.luque.model2gether;

import br.com.websocket.HandlerDiagram;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leand
 */
public class NoPollingServer extends HttpServlet {

//    private ActionPool actionPool;
    private MessagePoolSingleton messagePoolSingleton = MessagePoolSingleton.getInstance();
    private static final Set<HandlerDiagram> peers = Collections.synchronizedSet(new HashSet<HandlerDiagram>());
    private final JsonParser parser = new JsonParser();
    private final Gson gson = new Gson();
    public static final Map<HttpSession, String> idByHttpSession = new HashMap();

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.

//        actionPool = ActionPool.getInstance();
    }

    private static final long TIMEOUT_SEC = 20;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

//        out.println("{");
//        for (int i = 0; i < 4096; i++) {
//            out.println("");
//        }
//        out.println("}");
        int idValue = -1;
        try {
            idValue = Integer.valueOf(request.getParameter("id"));
        } catch (Exception e) {
            System.out.println("ID inválido");
            return;
        }

        br.com.models.User user = (br.com.models.User) request.getSession().getAttribute("user");
        long initialTime = System.currentTimeMillis();

        while (true) {
            if ((System.currentTimeMillis() - initialTime) > TIMEOUT_SEC * 1000) {
                break;
            }
//            Client client = new Client(clientId);
//            if (modelActionPool.hasNextActionFor(client)) {
            //System.out.println("Procurando mensagem de " + user.toString() + " para o diagrama " + request.getParameter("id"));
            if (messagePoolSingleton.hasMessage(user.toString(), request.getParameter("id"))) {
                System.out.print("===== Existem mensagens para o usuário " + user.toString() + ": ");
                System.out.println(messagePoolSingleton.hasMessage(user.toString(), request.getParameter("id")));
            }

            if (messagePoolSingleton.hasMessage(user.toString(), request.getParameter("id"))) {
                String message = messagePoolSingleton.nextMessage(user.toString(), request.getParameter("id"));
                System.out.println("===========================================");
                System.out.println("Mensagem recebida: " + message);

                out.println(message);
                out.flush();

                System.out.println("Mensagem enviada... " + message.length() + " bytes.");
                System.out.println("===========================================");
            }

            try {
                Thread.sleep(400);
            } catch (InterruptedException error) {
                error.printStackTrace();
            }
        }

//        if (request.getParameter("init") != null) {
//            reusedWebsocket.onOpen(request.getSession(), request.getParameter("id"));
//        }
//
//        String modelId = request.getParameter("id");
//        String clientId = request.getSession().getId();
//        ModelActionPool modelActionPool = actionPool.getFor(modelId);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
