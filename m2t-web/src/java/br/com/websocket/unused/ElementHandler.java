package br.com.websocket.unused;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.google.gson.Gson;

/**
 *
 * @author Leandro Luque
 */
class ElementHandler {
    
    private String msg;
    
    private int id;
    
    private float x;
    
    private float y;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ElementHandler(ElementHandler elm) {
        this.msg = elm.getMsg();
        this.id = elm.getId();
        this.x = elm.getX();
        this.y = elm.getY();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
    
}
