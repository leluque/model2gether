package br.com.luque.model2gether;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
public class MessagePoolSingleton {

    private boolean opened;
    private static final MessagePoolSingleton singleton = new MessagePoolSingleton();
    private Map<String, Map<String, List<String>>> messagesPerUserAndDiagram = new HashMap<>();

    private MessagePoolSingleton() {
    }

    public static MessagePoolSingleton getInstance() {
        return singleton;
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void open() {
        this.opened = true;
    }

    public void addMessage(String user, String diagram, String message) {
        Map<String, List<String>> userMap = messagesPerUserAndDiagram.get(user);
        if (userMap == null) {
            userMap = new HashMap<>();
            messagesPerUserAndDiagram.put(user, userMap);
        }
        List<String> messages = userMap.get(diagram);
        if (messages == null) {
            messages = new ArrayList<>();
            userMap.put(diagram, messages);
        }
        messages.add(message);
    }

    public boolean hasMessage(String user, String diagram) {
        Map<String, List<String>> userMap = messagesPerUserAndDiagram.get(user);
        if (userMap == null) {
            return false;
        } else {
            List<String> messages = userMap.get(diagram);
            return !(messages == null || messages.isEmpty());
        }
    }

    public String nextMessage(String user, String diagram) {
        Map<String, List<String>> userMap = messagesPerUserAndDiagram.get(user);
        if (userMap == null) {
            return null;
        } else {
            List<String> messages = userMap.get(diagram);
            if (messages == null || messages.isEmpty()) {
                return null;
            } else {
                try {
                    String message = messages.get(0);
                    messages.remove(0);
                    return message;
                } catch (Exception error) {
                    return null;
                }
            }
        }
    }

    //private Map<String, Map<String, 
}
