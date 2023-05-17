package com.race.app.command.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.race.app.command.domain.Distance;
import com.race.app.command.domain.RaceApplication;
import com.race.app.command.domain.RaceApplicationEvent;
import com.race.app.command.exceptions.RaceAppException;
import com.race.app.command.exceptions.RaceApplicationNotFoundException;
import com.race.app.command.kafka.KafkaProducer;
import com.race.app.command.repositories.RaceApplicationRepository;

@ExtendWith(MockitoExtension.class)
public class RaceApplicationCommandServiceImplTest
{

    @Mock
    private RaceApplicationRepository raceApplicationRepository;

    @Mock
    private KafkaProducer kafkaProducer;

    @InjectMocks
    private RaceApplicationCommandServiceImpl raceApplicationCommandService;

    private RaceApplication raceApplication;

    @BeforeEach
    void setUp()
    {
        raceApplication = new RaceApplication();
        raceApplication.setId(UUID.randomUUID());
        raceApplication.setFirstName("First");
        raceApplication.setLastName("Last");
        raceApplication.setDistance(Distance.MARATHON);
    }

    @Test
    void testCreateRaceApplication()
    {
        // Given
        when(raceApplicationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(raceApplicationRepository.save(any(RaceApplication.class))).thenReturn(raceApplication);
        doNothing().when(kafkaProducer).sendMessage(any(RaceApplicationEvent.class));

        // When
        RaceApplication savedRaceApplication = raceApplicationCommandService.create(raceApplication);

        // Then
        assertEquals(raceApplication, savedRaceApplication);
    }

    @Test
    void testPatchRaceApplication()
    {
        // Given
        RaceApplication raceApplicationNew = new RaceApplication();
        raceApplicationNew.setId(raceApplication.getId());
        raceApplicationNew.setFirstName("First");
        raceApplicationNew.setLastName("Last");
        raceApplicationNew.setClub("Club");
        raceApplicationNew.setDistance(Distance.MARATHON);

        Map<String, Object> fields = new HashMap<>();
        fields.put("club", "Club");
        when(raceApplicationRepository.findById(any(UUID.class))).thenReturn(Optional.of(raceApplication));
        when(raceApplicationRepository.save(any(RaceApplication.class))).thenReturn(raceApplication);
        doNothing().when(kafkaProducer).sendMessage(any(RaceApplicationEvent.class));

        // When
        RaceApplication updatedRaceApplication = raceApplicationCommandService.patch(raceApplication.getId(), fields);

        // Then
        assertEquals(raceApplicationNew.getId(), updatedRaceApplication.getId());
        assertEquals(raceApplicationNew.getClub(), updatedRaceApplication.getClub());
    }

    @Test
    void testDeleteRaceApplication()
    {
        // Given
        doNothing().when(raceApplicationRepository).delete(any(RaceApplication.class));
        doNothing().when(kafkaProducer).sendMessage(any(RaceApplicationEvent.class));

        // When and Then
        assertDoesNotThrow(() -> raceApplicationCommandService.delete(raceApplication));
    }

    @Test
    void testFindById()
    {
        // Given
        when(raceApplicationRepository.findById(any(UUID.class))).thenReturn(Optional.of(raceApplication));

        // When
        RaceApplication foundRaceApplication = raceApplicationCommandService.findById(raceApplication.getId());

        // Then
        assertEquals(raceApplication, foundRaceApplication);
    }

    @Test
    void testCreateRaceApplicationAlreadyExists()
    {
        // Given
        when(raceApplicationRepository.findById(any(UUID.class))).thenReturn(Optional.of(raceApplication));

        // When and Then
        assertThrows(RaceAppException.class, () -> raceApplicationCommandService.create(raceApplication));
    }

    @Test
    void testPatchRaceApplicationNotFound()
    {
        // Given
        when(raceApplicationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        Map<String, Object> fields = new HashMap<>();

        // When and Then
        assertThrows(RaceAppException.class, () -> raceApplicationCommandService.patch(UUID.randomUUID(), fields));
    }

    @Test
    void testFindByIdNotFound()
    {
        // Given
        when(raceApplicationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // When and Then
        assertThrows(RaceApplicationNotFoundException.class,
                () -> raceApplicationCommandService.findById(UUID.randomUUID()));
    }

    @Test
    void testCreateRaceApplicationDbError()
    {
        // Given
        when(raceApplicationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(raceApplicationRepository.save(any(RaceApplication.class))).thenThrow(new DataAccessException("DB error")
        {
        });

        // When & Then
        assertThrows(DataAccessException.class, () -> raceApplicationCommandService.create(raceApplication));
    }

    @Test
    void testCreateRaceApplicationKafkaError()
    {
        // Given
        when(raceApplicationRepository.findById(any(UUID.class))).thenReturn(Optional.empty());
        when(raceApplicationRepository.save(any(RaceApplication.class))).thenReturn(raceApplication);
        doThrow(new RuntimeException("Kafka error")).when(kafkaProducer).sendMessage(any(RaceApplicationEvent.class));

        // When & Then
        assertThrows(RuntimeException.class, () -> raceApplicationCommandService.create(raceApplication));
    }

    @Test
    void testDeleteRaceApplicationDbError()
    {
        // Given
        doThrow(new RaceAppException("DB error") {}).when(raceApplicationRepository).delete(any(RaceApplication.class));

        // When & Then
        assertThrows(RaceAppException.class, () -> raceApplicationCommandService.delete(raceApplication));
    }

    @Test
    void testDeleteRaceApplicationKafkaError()
    {
        // Given
        doNothing().when(raceApplicationRepository).delete(any(RaceApplication.class));
        doThrow(new RuntimeException("Kafka error")).when(kafkaProducer).sendMessage(any(RaceApplicationEvent.class));

        // When & Then
        assertThrows(RuntimeException.class, () -> raceApplicationCommandService.delete(raceApplication));
    }
}
