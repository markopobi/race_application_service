package com.race.app.race_application_command_service.kafka;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.race.app.race_application_command_service.domain.RaceApplication;
import com.race.app.race_application_command_service.domain.RaceApplicationEvent;

@ExtendWith(MockitoExtension.class)
public class KafkaProducerTest
{

    @Mock
    private KafkaTemplate<String, RaceApplicationEvent> kafkaTemplate;

    @InjectMocks
    private KafkaProducer kafkaProducer;

    @Test
    public void testSendMessage()
    {
        // Given
        RaceApplicationEvent event = new RaceApplicationEvent("CREATE", new RaceApplication());

        // When
        kafkaProducer.sendMessage(event);

        // Then
        verify(kafkaTemplate, times(1)).send(KafkaProducer.TOPIC, event);
    }
}