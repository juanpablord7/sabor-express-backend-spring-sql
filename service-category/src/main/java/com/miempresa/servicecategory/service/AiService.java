package com.miempresa.servicecategory.service;


import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class AiService {
    private final ChatModel chatModel;

    public AiService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public ChatResponse gemini() {
        return chatModel.call(
                new Prompt("Generate the names of 5 famous pirates.")
        );
    }

    /*
    public ChatResponse gpt(){
        ChatResponse response = chatModel.call(
                new Prompt(
                        "Cuantas peliculas de harry potter hay?",
                        OpenAiChatOptions.builder()
                                .model("gpt-4o")
                                .temperature(0.4)
                                .build()
                ));

        return response;
    }
    */



}
