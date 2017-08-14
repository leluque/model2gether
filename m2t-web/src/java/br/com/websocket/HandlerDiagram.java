/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.websocket;

import br.com.models.UseCaseDiagram;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpSession;

/**
 *
 * @author leand
 */
public class HandlerDiagram {

    public UseCaseDiagram diagram = new UseCaseDiagram();
    public Set<HttpSession> peers = Collections.synchronizedSet(new HashSet<HttpSession>());
    public boolean isOpened = false;
}
