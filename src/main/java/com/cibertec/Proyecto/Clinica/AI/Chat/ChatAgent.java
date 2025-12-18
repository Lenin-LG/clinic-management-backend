package com.cibertec.Proyecto.Clinica.AI.Chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatAgent {

    private final ChatClient chatClient;

    public ChatAgent(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String procesar(String conversationId, String mensaje) {
        return chatClient
                .prompt()
                .advisors(a -> a.param("conversationId", conversationId))
                .user(mensaje)
                .call()
                .content();
    }

}
