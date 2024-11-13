package com.example.recipesuggestion.controller;
import com.example.recipesuggestion.dto.ChatRequest;
import com.example.recipesuggestion.dto.ChatResponse;
import com.example.recipesuggestion.dto.Choice;
import com.example.recipesuggestion.dto.Usage;
import com.example.recipesuggestion.dto.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class RecipeController {
    @Value("${OPENAI_API_KEY}")
    private String openapikey;

    private final WebClient webClient;

    public RecipeController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://api.openai.com/v1/chat/completions").build();
    }

    @GetMapping("/chat")
    public Map<String, Object> chatWithGPT(@RequestParam String message) {
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-3.5-turbo");

        List<Message> lstMessages = new ArrayList<>();
        lstMessages.add(new Message("system", "You are a helpful assistant."));
        lstMessages.add(new Message("user", "Where is " + message));

        chatRequest.setMessages(lstMessages);
        chatRequest.setN(3);
        chatRequest.setTemperature(2.1);
        chatRequest.setMaxTokens(30);
        chatRequest.setStream(false);
        chatRequest.setPresencePenalty(1.2);

        ChatResponse response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(openapikey))
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        List<Choice> lst = response.getChoices();
        Usage usg = response.getUsage();

        Map<String, Object> map = new HashMap<>();
        map.put("Usage", usg);
        map.put("Choices", lst);

        return map;
    }

    @GetMapping("/recipeSuggestion")
    public Map<String, Object> recipeSuggestion(@RequestParam String ingredients) {
        ChatRequest chatRequest = new ChatRequest();
        chatRequest.setModel("gpt-3.5-turbo");

        List<Message> lstMessages = new ArrayList<>();
        lstMessages.add(new Message("system", "You are a professional chef."));
        lstMessages.add(new Message("user", "Can you suggest a recipe using the following ingredients: " + ingredients + "?"));

        chatRequest.setMessages(lstMessages);
        chatRequest.setN(1);
        chatRequest.setTemperature(0.7);
        chatRequest.setMaxTokens(150);
        chatRequest.setStream(false);
        chatRequest.setPresencePenalty(0.5);

        ChatResponse response = webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .headers(h -> h.setBearerAuth(openapikey))
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToMono(ChatResponse.class)
                .block();

        List<Choice> lst = response.getChoices();
        Usage usg = response.getUsage();

        Map<String, Object> map = new HashMap<>();
        map.put("Usage", usg);
        map.put("RecipeSuggestion", lst);

        return map;
    }
}
