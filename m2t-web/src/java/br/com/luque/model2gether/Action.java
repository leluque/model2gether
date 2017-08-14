package br.com.luque.model2gether;

import java.util.Map;

/**
 *
 * @author leand
 */
public interface Action {
    
    public Element getTarget();
    public Map<String, String> getData();
    public String getDefaultData();
    public Client getClient();
    public User getUser();
    
}
