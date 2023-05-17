package com.race.app.query.controllers;

import java.util.Collections;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.race.app.query.domain.RaceApplication;
import com.race.app.query.exceptions.RaceApplicationNotFoundException;
import com.race.app.query.services.RaceApplicationQueryService;

public class RaceApplicationQueryControllerTest
{

    @Mock
    RaceApplicationQueryService raceApplicationQueryService;

    RaceApplicationQueryController raceApplicationQueryController;

    MockMvc mockMvc;

    @BeforeEach
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);

        raceApplicationQueryController = new RaceApplicationQueryController(raceApplicationQueryService);
        mockMvc = MockMvcBuilders.standaloneSetup(raceApplicationQueryController).build();
    }

    @Test
    void getRaceApplication() throws Exception
    {
        // Given
        UUID id = UUID.randomUUID();
        RaceApplication raceApplication = new RaceApplication();
        raceApplication.setId(id);
        when(raceApplicationQueryService.getRaceApplication(id)).thenReturn(raceApplication);

        // When & Then
        mockMvc.perform(get("http://localhost:8081/api/race-applications/" + id)).andExpect(status().isOk());

        verify(raceApplicationQueryService, times(1)).getRaceApplication(any());
    }

    @Test
    void getAllRaceApplications() throws Exception
    {
        // Given
        UUID id = UUID.randomUUID();
        RaceApplication raceApplication = new RaceApplication();
        raceApplication.setId(id);
        when(raceApplicationQueryService.getRaceApplication(id)).thenReturn(raceApplication);

        // When & Then
        mockMvc.perform(get("http://localhost:8081/api/race-applications/")).andExpect(status().isOk());

        verify(raceApplicationQueryService, times(1)).getAllRaceApplications();
    }

    @Test
    void getRaceApplication_NotFound() throws Exception
    {
        // Given
        UUID id = UUID.randomUUID();
        when(raceApplicationQueryService.getRaceApplication(id)).thenThrow(RaceApplicationNotFoundException.class);

        // When & Then
        mockMvc.perform(get("http://localhost:8081/api/race-applications/" + id)).andExpect(status().isNotFound());
    }

    @Test
    void getAllRaceApplications_EmptyList() throws Exception
    {
        // Given
        when(raceApplicationQueryService.getAllRaceApplications()).thenReturn(Collections.emptyList());

        // When & Then
        mockMvc.perform(get("http://localhost:8081/api/race-applications/")).andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}
