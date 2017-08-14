package br.com.websocket.unused;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.Gson;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Leandro Luque
 */
public class ElementDecoder implements Decoder.Text<ElementHandler> {

    @Override
    public ElementHandler decode(String string) throws DecodeException {
        ElementHandler elm = new Gson().fromJson(string, ElementHandler.class);
        return new ElementHandler(elm);
    }

    @Override
    public boolean willDecode(String string) {
        try {
            new Gson().fromJson(string, ElementHandler.class);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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
