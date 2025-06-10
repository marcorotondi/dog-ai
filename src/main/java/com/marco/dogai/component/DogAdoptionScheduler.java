package com.marco.dogai.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class DogAdoptionScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DogAdoptionScheduler.class);

    @Tool(description = "schedule an appointment to pickup or adopt a " +
                        "dog from a Pooch Palace location")
    public String schedule(int dogId, String dogName) {
        logger.info("Scheduling adoption for dog {}", dogName);
        return Instant
                .now()
                .plus(3, ChronoUnit.DAYS)
                .toString();
    }
}