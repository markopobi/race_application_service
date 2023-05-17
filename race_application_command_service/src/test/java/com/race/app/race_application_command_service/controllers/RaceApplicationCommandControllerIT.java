package com.race.app.race_application_command_service.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.race.app.race_application_command_service.domain.Distance;
import com.race.app.race_application_command_service.domain.RaceApplication;
import com.race.app.race_application_command_service.repositories.RaceApplicationRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@WithMockUser(authorities = { "race_application:write", "race_application:patch", "race_application:delete" })
public class RaceApplicationCommandControllerIT
{

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RaceApplicationRepository raceApplicationRepository;

    @AfterEach
    public void tearDown()
    {
        raceApplicationRepository.deleteAll();
    }

    @Test
    public void testCreateRaceApplication() throws Exception
    {
        // Prepare a race application
        RaceApplication raceApplication = new RaceApplication();
        raceApplication.setId(UUID.randomUUID());
        raceApplication.setFirstName("First");
        raceApplication.setLastName("Last");
        raceApplication.setClub("Club");
        raceApplication.setDistance(Distance.MARATHON);

        // Perform the post request
        mockMvc.perform(post("/api/race-application").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(raceApplication)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(raceApplication.getId().toString())))
                .andExpect(jsonPath("$.firstName", is("First")))
                .andExpect(jsonPath("$.lastName", is("Last")))
                .andExpect(jsonPath("$.club", is("Club")))
                .andExpect(jsonPath("$.distance", is(Distance.MARATHON.toString())));
    }

    @Test
    public void testPatchRaceApplication() throws Exception
    {
        // Prepare a race application and save it
        RaceApplication existingRaceApplication = new RaceApplication();
        existingRaceApplication.setId(UUID.randomUUID());
        existingRaceApplication.setFirstName("First");
        existingRaceApplication.setLastName("Last");
        existingRaceApplication.setClub("Club");
        existingRaceApplication.setDistance(Distance.MARATHON);
        RaceApplication savedRaceApplication = raceApplicationRepository.save(existingRaceApplication);

        // Prepare the patch
        Map<String, Object> updates = new HashMap<>();
        updates.put("firstName", "UpdatedFirst");

        // Perform the patch request
        mockMvc.perform(
                        patch("/api/race-application/" + savedRaceApplication.getId()).contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updates)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedRaceApplication.getId().toString())))
                .andExpect(jsonPath("$.firstName", is("UpdatedFirst")))
                .andExpect(jsonPath("$.lastName", is("Last")))
                .andExpect(jsonPath("$.club", is("Club")))
                .andExpect(jsonPath("$.distance", is(Distance.MARATHON.toString())));
    }

    @Test
    public void testDeleteRaceApplication() throws Exception
    {
        // Prepare a race application and save it
        RaceApplication existingRaceApplication = new RaceApplication();
        existingRaceApplication.setId(UUID.randomUUID());
        existingRaceApplication.setFirstName("First");
        existingRaceApplication.setLastName("Last");
        existingRaceApplication.setClub("Club");
        existingRaceApplication.setDistance(Distance.MARATHON);
        RaceApplication savedRaceApplication = raceApplicationRepository.save(existingRaceApplication);

        // Perform the delete request
        mockMvc.perform(
                        delete("/api/race-application/" + savedRaceApplication.getId()).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify that the race application is deleted
        assertFalse(raceApplicationRepository.findById(savedRaceApplication.getId()).isPresent());
    }
}
