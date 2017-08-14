package br.com.luque.model2gether;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author leand
 */
public class ModelActionPool {

    private final DoublyLinkedList<Action> actions = new DoublyLinkedList<>();
    private final Map<Client, Iterator<Action>> currentClientsAction = new HashMap();

    public ModelActionPool() {
        this.addClient(new Client("1"));
        this.addClient(new Client("2"));
        this.addClient(new Client("3"));
    }
    
    public void addAction(Action action) {
        this.actions.add(action);
    }

    public void addClient(Client client) {
        this.currentClientsAction.put(client, this.actions.iterator());
    }

    public boolean hasNextActionFor(Client client) {
        return this.currentClientsAction.get(client).hasNext();
    }

    public Action nextActionFor(Client client) {
        Iterator<Action> clientIterator = this.currentClientsAction.get(client);
        if (!clientIterator.hasNext()) {
            return null;
        }
        return clientIterator.next();
    }

}
