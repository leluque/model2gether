/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.websocket.unused;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Leandro Luque
 */
/*
@ServerEndpoint(value = "/uml_endpoint"/*,
        encoders = {ElementEncoder.class},
        decoders = {ElementDecoder.class}*)*/

public class UMLDiagrams {

    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    /* Nao parece ser util, pelo menos por enquanto
    @OnMessage
    public void broadcastElement(ElementHandler handler, Session session) throws IOException, EncodeException {
        System.out.println("broadcastFigure: " + handler);
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendObject(handler);
            }
        }
    }
    */
    
    @OnMessage
    public void broadcastMessage(String string, Session session) throws IOException, EncodeException {
        System.out.println("broadcastFigure: " + string);
        for (Session peer : peers) {
            if (!peer.equals(session)) {
                peer.getBasicRemote().sendText(string);
            }
        }
    }
    
    @OnOpen
    public void onOpen(Session peer) {
        peers.add(peer);
    }

    @OnClose
    public void onClose(Session peer) {
        peers.remove(peer);
    }
    
}
