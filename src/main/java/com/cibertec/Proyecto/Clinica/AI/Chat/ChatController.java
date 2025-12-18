package com.cibertec.Proyecto.Clinica.AI.Chat;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatAgent chatAgent;

    @PostMapping
    public String chat(
            @RequestParam String conversationId,
            @RequestBody String mensaje
    ) {
        return chatAgent.procesar(conversationId, mensaje);
    }

}
