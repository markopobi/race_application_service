package com.race.app.race_application_query_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.race.app.race_application_query_service.domain.RaceApplicationEvent;
import com.race.app.race_application_query_service.services.RaceApplicationQueryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumer
{

    private final RaceApplicationQueryService raceApplicationQueryService;

    public KafkaConsumer(RaceApplicationQueryService raceApplicationQueryService)
    {
        this.raceApplicationQueryService = raceApplicationQueryService;
    }

    @KafkaListener(topics = "race_applications", groupId = "group_id")
    public void consume(RaceApplicationEvent raceApplicationEvent)
    {
        log.debug("Calling method consume from Kafka Consumer...");
        raceApplicationQueryService.consume(raceApplicationEvent);
    }
}
