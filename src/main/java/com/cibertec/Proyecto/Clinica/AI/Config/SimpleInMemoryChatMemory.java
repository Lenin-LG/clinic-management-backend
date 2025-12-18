package com.cibertec.Proyecto.Clinica.AI.Config;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SimpleInMemoryChatMemory implements ChatMemory {

    private final Map<String, List<Message>> store = new HashMap<>();

    @Override
    public void add(String conversationId, Message message) {
        store.computeIfAbsent(conversationId, k -> new ArrayList<>()).add(message);
    }

    @Override
    public void add(String conversationId, List<Message> messages) {
        store.computeIfAbsent(conversationId, k -> new ArrayList<>()).addAll(messages);
    }

    @Override
    public List<Message> get(String conversationId) {
        return store.getOrDefault(conversationId, Collections.emptyList());
    }

    @Override
    public void clear(String conversationId) {
        store.remove(conversationId);
    }
}