/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.luque.model2gether;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author leand
 */
public class ActionImpl implements Action {

    private Element target;
    private Map<String, String> data = new HashMap<>();
    private Client client;
    private User user;
    private static final String DEFAULT_DATA_KEY = "DEFAULT_DATA";

    public ActionImpl() {
    }

    public ActionImpl(Client client) {
        this.client = client;
    }
    
    @Override
    public Element getTarget() {
        return this.target;
    }

    public void setTarget(Element target) {
        this.target = target;
    }

    @Override
    public Map<String, String> getData() {
        return this.data;
    }

    @Override
    public String getDefaultData() {
        return this.data.get(DEFAULT_DATA_KEY);
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
