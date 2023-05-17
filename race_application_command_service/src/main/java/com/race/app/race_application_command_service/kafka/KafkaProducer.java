package com.race.app.race_application_command_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.race.app.race_application_command_service.domain.RaceApplicationEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaProducer
{

    static final String TOPIC = "race_applications";

    private final KafkaTemplate<String, RaceApplicationEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, RaceApplicationEvent> kafkaTemplate)
    {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(RaceApplicationEvent raceApplicationEvent)
    {
        log.debug("Sending message from KafkaProducer...");
        kafkaTemplate.send(TOPIC, raceApplicationEvent);
    }

}
