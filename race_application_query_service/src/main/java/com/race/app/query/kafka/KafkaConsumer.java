package com.race.app.query.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.race.app.query.domain.RaceApplicationEvent;
import com.race.app.query.services.RaceApplicationQueryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumer {

    @Autowired
    private RaceApplicationQueryService raceApplicationQueryService;

    @KafkaListener(topics = "race_applications", groupId = "group_id")
    public void consume(RaceApplicationEvent raceApplicationEvent) {
        log.debug("Calling method consume from Kafka Consumer...");
        raceApplicationQueryService.consume(raceApplicationEvent);
    }
}
