package com.rumor.yumback.domains.diets.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.Media;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GeminiClient {
    private final VertexAiGeminiChatModel vertexAiGeminiChatModel;

    public String chat(String message) {
        return vertexAiGeminiChatModel.call(message);
    }

    public ChatResponse chat(String message, List<Media> imageData) {
        UserMessage userMessage = new UserMessage(message, imageData);
        return vertexAiGeminiChatModel.call(new Prompt(userMessage));
    }
}
