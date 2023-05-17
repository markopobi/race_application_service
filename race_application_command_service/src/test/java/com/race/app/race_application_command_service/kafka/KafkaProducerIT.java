package com.race.app.race_application_command_service.kafka;

import java.util.UUID;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

import com.race.app.race_application_command_service.domain.Distance;
import com.race.app.race_application_command_service.domain.RaceApplication;
import com.race.app.race_application_command_service.domain.RaceApplicationEvent;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { KafkaProducerIT.TOPIC })
public class KafkaProducerIT
{

    static final String TOPIC = "race_applications";

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    private KafkaProducer kafkaProducer;

    private Consumer<String, RaceApplicationEvent> consumer;

    @BeforeEach
    void setUp()
    {
        var consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);
        consumer = new DefaultKafkaConsumerFactory<String, RaceApplicationEvent>(consumerProps).createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, TOPIC);
    }

    @AfterEach
    void tearDown()
    {
        consumer.close();
    }

    @Test
    void whenSendMessage_thenMessageReceived()
    {
        // Given
        RaceApplication raceApplication = new RaceApplication();
        raceApplication.setId(UUID.randomUUID());
        raceApplication.setFirstName("First");
        raceApplication.setLastName("Last");
        raceApplication.setDistance(Distance.MARATHON);
        RaceApplicationEvent event = new RaceApplicationEvent("CREATE", raceApplication);

        // When
        kafkaProducer.sendMessage(event);

        // Then
        ConsumerRecord<String, RaceApplicationEvent> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TOPIC);
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.value()).isEqualTo(event);
    }

}
