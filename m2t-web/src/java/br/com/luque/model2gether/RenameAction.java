package br.com.luque.model2gether;

/**
 *
 * @author leand
 */
public class RenameAction extends ActionImpl {

    public RenameAction() {
    }

    public RenameAction(Client client) {
        super(client);
    }
    
    @Override
    public String toString() {
        return "Client: " + getClient() + " - Action: RENAME.";
    }

}
