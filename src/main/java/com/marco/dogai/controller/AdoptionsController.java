package com.marco.dogai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

@Controller
@ResponseBody
public class AdoptionsController {

    private final ChatClient ai;

    public AdoptionsController(ChatClient.Builder ai,
                               PromptChatMemoryAdvisor promptChatMemoryAdvisor) {
        this.ai = ai
                .defaultAdvisors(promptChatMemoryAdvisor)
                .build();
    }


    @GetMapping("/{user}/assistant")
    String inquire(@PathVariable String user, @RequestParam String question) {
        var content = ai
                .prompt()
                .user(question)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, user))
                .call()
                .content();

        return Objects.requireNonNull(content)
                .replace("<think>", "")
                .replace("</think>", "")
                .trim();
    }
}