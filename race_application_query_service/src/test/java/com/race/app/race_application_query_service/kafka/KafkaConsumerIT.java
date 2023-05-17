package com.race.app.race_application_query_service.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import com.race.app.race_application_query_service.domain.RaceApplication;
import com.race.app.race_application_query_service.domain.RaceApplicationEvent;
import com.race.app.race_application_query_service.services.RaceApplicationQueryService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@DirtiesContext
public class KafkaConsumerIT
{

    private static final String TOPIC = "race_applications";

    private KafkaTemplate<String, RaceApplicationEvent> kafkaTemplate;

    @MockBean
    private RaceApplicationQueryService raceApplicationQueryService;

    @BeforeEach
    public void setUp()
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        ProducerFactory<String, RaceApplicationEvent> producerFactory = new DefaultKafkaProducerFactory<>(configs);
        kafkaTemplate = new KafkaTemplate<>(producerFactory);
    }

    @Test
    public void testConsume()
    {
        // Given
        RaceApplicationEvent event = new RaceApplicationEvent("CREATE", new RaceApplication());

        //When
        kafkaTemplate.send(TOPIC, event);

        //Then
        verify(raceApplicationQueryService, timeout(5000)).consume(event);
    }
}