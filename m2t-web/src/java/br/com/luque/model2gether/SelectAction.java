package br.com.luque.model2gether;

/**
 *
 * @author leand
 */
public class SelectAction extends ActionImpl {

    public SelectAction() {
    }

    public SelectAction(Client client) {
        super(client);
    }
    
    @Override
    public String toString() {
        return "Client: " + getClient() + " - Action: SELECT.";
    }

}
