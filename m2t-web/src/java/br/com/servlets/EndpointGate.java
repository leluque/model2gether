package br.com.servlets;

import br.com.websocket.UMLDiagramEndpoint;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 * This class serves as an endpoint client to the websocket. It was created to
 * overcome some difficulties we have found when working with websockets in some
 * browsers and computers.
 *
 * When accessing this servlet, the following types of requests may be made: -
 * Open a connection to an endpoint. - Send message - Get any new message
 *
 * To each of them, it is necessary to inform the diagram id.
 *
 * @author Leandro Luque.
 */
@ClientEndpoint
public class EndpointGate extends HttpServlet {

    // List of messages per diagram.
    private Map<String, Session> sessionByDiagram = new HashMap();
    private Map<String, List<String>> messagesByDiagram = new HashMap();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String diagramId = request.getParameter("id");
        String messageIndex = request.getParameter("messageIndex");
        String getMessage = request.getParameter("getMessage");
        String messageToSend = request.getParameter("messageToSend");
        String open = request.getParameter("open");
        if (diagramId != null) {
            if (open != null) {
                try {
                    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                    System.out.println(("ws://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/umldiagram_endpoint/" + diagramId));
                    container.connectToServer(this, new URI(("ws://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/umldiagram_endpoint/" + diagramId)));
                } catch (URISyntaxException | DeploymentException | IOException exception) {
                    exception.printStackTrace();
                    throw new RuntimeException(exception);
                }
            } else if (messageToSend != null) {
                sendMessage(messageToSend, diagramId);
            } else if (getMessage != null) {
                if (!this.messagesByDiagram.containsKey(diagramId)) {
                    throw new RuntimeException("The websocket has not been opened.");
                }
                if (this.messagesByDiagram.get(diagramId).size() > Integer.valueOf(messageIndex)) {
                    this.messagesByDiagram.get(diagramId).get(Integer.valueOf(messageIndex));
                }
            }
        } else {
            System.out.println("The diagram id was not informed to the websocket gate.");
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        sendMessage(userSession, "give-me-the-id");
    }

    /**
     * Callback hook for Connection close events.
     *
     * @param userSession the userSession which is getting closed.
     * @param reason the reason for connection close
     */
    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
    }

    /**
     * Callback hook for Message Events. This method will be invoked when a
     * client send a message.
     *
     * @param message The text message
     * @param session The websocket session.
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        if (message.contains("websocket-opened:")) {
            String[] parts = message.split(":");
            String diagramId = parts[1];
            if (!this.messagesByDiagram.containsKey(diagramId)) {
                this.messagesByDiagram.put(diagramId, new ArrayList());
            }

        }
    }

    public void sendMessage(String id, String message) {
        this.sessionByDiagram.get(id).getAsyncRemote().sendText(message);
    }

    public void sendMessage(Session session, String message) {
        session.getAsyncRemote().sendText(message);
    }

}
