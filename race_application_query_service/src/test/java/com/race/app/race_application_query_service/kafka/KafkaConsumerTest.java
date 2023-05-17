package com.race.app.race_application_query_service.kafka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

import com.race.app.race_application_query_service.domain.RaceApplication;
import com.race.app.race_application_query_service.domain.RaceApplicationEvent;
import com.race.app.race_application_query_service.services.RaceApplicationQueryService;

@ExtendWith(MockitoExtension.class)
public class KafkaConsumerTest
{

    @Mock
    private RaceApplicationQueryService raceApplicationQueryService;

    @InjectMocks
    private KafkaConsumer kafkaConsumer;

    @BeforeEach
    public void setUp()
    {
        raceApplicationQueryService = Mockito.mock(RaceApplicationQueryService.class);
        kafkaConsumer = new KafkaConsumer(raceApplicationQueryService);
    }

    @Test
    public void testConsume()
    {
        // Given
        RaceApplicationEvent event = new RaceApplicationEvent("CREATE", new RaceApplication());

        // When
        kafkaConsumer.consume(event);

        // Then
        verify(raceApplicationQueryService).consume(event);
    }
}