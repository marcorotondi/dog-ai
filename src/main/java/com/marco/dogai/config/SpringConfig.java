package com.marco.dogai.config;

import com.marco.dogai.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

@Configuration
public class SpringConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringConfig.class);

    /*
    Advisors are like filters or interceptors. They're a great way to add to the body of a request or handle the response in a generic, cross-cutting kind of way.
    Sort of like Spring's aspect-oriented programming support.
    This advisor will persist the messages for you.
     */
    @Bean
    public PromptChatMemoryAdvisor promptChatMemoryAdvisor(DataSource dataSource) {
        var jdbc = JdbcChatMemoryRepository
                .builder()
                .dataSource(dataSource)
                .build();

        var chatMessageWindow = MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(jdbc)
                .build();

        return PromptChatMemoryAdvisor
                .builder(chatMessageWindow)
                .build();
    }
}