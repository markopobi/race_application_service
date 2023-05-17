package com.race.app.race_application_query_service.controllers;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.race.app.race_application_query_service.domain.Distance;
import com.race.app.race_application_query_service.domain.RaceApplication;
import com.race.app.race_application_query_service.domain.RaceApplicationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RaceApplicationQueryControllerIT
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RaceApplicationRepository raceApplicationRepository;

    @BeforeEach
    public void setup()
    {
        RaceApplication raceApplication = new RaceApplication();
        raceApplication.setFirstName("First");
        raceApplication.setLastName("Last");
        raceApplication.setClub("Club");
        raceApplication.setDistance(Distance.MARATHON);

        raceApplicationRepository.save(raceApplication);
    }

    @Test
    public void getAllRaceApplications_Exists_ReturnsRaceApplications() throws Exception
    {

        mockMvc.perform(get("/api/race-applications").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].firstName").value("First"))
                .andExpect(jsonPath("$.[0].lastName").value("Last"))
                .andExpect(jsonPath("$.[0].club").value("Club"))
                .andExpect(jsonPath("$.[0].distance").value(Distance.MARATHON.toString()));
    }

    @Test
    public void getRaceApplication_ValidId_ReturnsRaceApplication() throws Exception
    {
        String firstRaceApplicationId = raceApplicationRepository.findAll().stream().findFirst().get().getId()
                .toString();
        mockMvc.perform(
                        get("/api/race-applications/" + firstRaceApplicationId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(firstRaceApplicationId))
                .andExpect(jsonPath("$.firstName").value("First"))
                .andExpect(jsonPath("$.lastName").value("Last"))
                .andExpect(jsonPath("$.club").value("Club"))
                .andExpect(jsonPath("$.distance").value(Distance.MARATHON.toString()));
    }

    @Test
    public void getRaceApplication_InvalidId_ReturnsNotFound() throws Exception
    {
        UUID invalidId = UUID.randomUUID();
        mockMvc.perform(get("/api/race-applications/" + invalidId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getRaceApplication_NonExistentId_ReturnsNotFound() throws Exception
    {
        UUID nonExistentId = UUID.randomUUID();
        mockMvc.perform(get("/api/race-applications/" + nonExistentId).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllRaceApplications_NoRaceApplications_ReturnsEmptyList() throws Exception
    {
        raceApplicationRepository.deleteAll();
        mockMvc.perform(get("/api/race-applications").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$").isArray()).andExpect(jsonPath("$").isEmpty());
    }
}
