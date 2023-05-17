package com.race.app.query.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.race.app.query.domain.Distance;
import com.race.app.query.domain.RaceApplication;
import com.race.app.query.domain.RaceApplicationEvent;
import com.race.app.query.domain.RaceApplicationRepository;
import com.race.app.query.exceptions.RaceApplicationNotFoundException;

@ExtendWith(MockitoExtension.class)
public class RaceApplicationQueryServiceImplTest
{

    @Mock
    RaceApplicationRepository raceApplicationRepository;

    @InjectMocks
    RaceApplicationQueryServiceImpl raceApplicationQueryService;

    @Test
    void getAllRaceApplications()
    {
        // Given
        RaceApplication raceApplication1 = new RaceApplication();
        raceApplication1.setId(UUID.randomUUID());

        RaceApplication raceApplication2 = new RaceApplication();
        raceApplication2.setId(UUID.randomUUID());

        when(raceApplicationRepository.findAll()).thenReturn(Arrays.asList(raceApplication1, raceApplication2));

        // When
        List<RaceApplication> raceApplicationList = raceApplicationQueryService.getAllRaceApplications();

        // Then
        assertEquals(2, raceApplicationList.size());
        verify(raceApplicationRepository, times(1)).findAll();
    }

    @Test
    void getRaceApplication()
    {
        // Given
        RaceApplication newRaceApplication = new RaceApplication();
        UUID id = UUID.randomUUID();
        newRaceApplication.setId(id);
        Optional<RaceApplication> raceApplicationOptional = Optional.of(newRaceApplication);

        when(raceApplicationRepository.findById(any())).thenReturn(raceApplicationOptional);

        // When
        RaceApplication raceApplication = raceApplicationQueryService.getRaceApplication(id);

        // Then
        assertEquals(id, raceApplication.getId());
        verify(raceApplicationRepository, times(1)).findById(any());
    }

    @Test
    public void getRaceApplication_NotFoundException()
    {
        // Given
        UUID id = UUID.randomUUID();
        when(raceApplicationRepository.findById(id)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RaceApplicationNotFoundException.class, () -> raceApplicationQueryService.getRaceApplication(id));
    }

    @Test
    public void consume_ValidCreateEvent_RaceApplicationSaved()
    {
        // Given
        UUID id = UUID.randomUUID();
        RaceApplication raceApplication = new RaceApplication();
        raceApplication.setId(id);
        raceApplication.setFirstName("First");
        raceApplication.setLastName("Last");
        raceApplication.setClub("Club");
        raceApplication.setDistance(Distance.MARATHON);

        RaceApplicationEvent raceApplicationEvent = new RaceApplicationEvent("CREATE", raceApplication);

        // When
        raceApplicationQueryService.consume(raceApplicationEvent);

        // Then
        verify(raceApplicationRepository).save(raceApplication);
    }

    @Test
    public void consume_InvalidEventType_IllegalArgumentException()
    {
        // Given
        UUID id = UUID.randomUUID();
        RaceApplication raceApplication = new RaceApplication();
        raceApplication.setId(id);
        raceApplication.setFirstName("First");
        raceApplication.setLastName("Last");
        raceApplication.setClub("Club");
        raceApplication.setDistance(Distance.MARATHON);
        RaceApplicationEvent raceApplicationEvent = new RaceApplicationEvent("INVALID_EVENT_TYPE", raceApplication);

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> raceApplicationQueryService.consume(raceApplicationEvent));
    }
}
