package com.marco.dogai.controller;

import com.marco.dogai.repository.DogRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
public class AdoptionsController {

    private static final String SYSTEM_PROMPT = """
            You are an AI powered assistant to help people adopt a dog from the adoption agency named Pooch
            Palace with locations in Rio de Janeiro, Mexico City, Seoul, Tokyo, Singapore, New York City, Amsterdam, Paris, Mumbai, New Delhi, Barcelona,
            London, and San Francisco. Information about the dogs available will be presented below. If there is no information,
            then return a polite response suggesting we don't have any dogs available.
            """;

    private final ChatClient ai;

    public AdoptionsController(ChatClient.Builder ai,
                               PromptChatMemoryAdvisor promptChatMemoryAdvisor,
                               VectorStore vectorStore) {

        this.ai = ai
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(promptChatMemoryAdvisor,
                        new QuestionAnswerAdvisor(vectorStore)
                )
                .build();
    }


    @GetMapping("/{user}/assistant")
    String inquire(@PathVariable String user, @RequestParam String question) {
        return ai
                .prompt()
                .user(question)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
                .call()
                //.entity(DogAdoptionSuggestion.class)
                .content();
    }
}