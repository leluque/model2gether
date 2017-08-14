package br.com.websocket.unused;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Leandro Luque
 */
public class ElementEncoder implements Encoder.Text<ElementHandler>{

    @Override
    public String encode(ElementHandler handler) throws EncodeException {
        return handler.toString();
    }

    @Override
    public void init(EndpointConfig config) {
        System.out.println("init");
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
    }
    
}
