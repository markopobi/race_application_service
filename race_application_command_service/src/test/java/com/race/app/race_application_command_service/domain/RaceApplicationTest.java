package com.race.app.race_application_command_service.domain;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RaceApplicationTest
{
    private static RaceApplication raceApplication;

    private static final UUID uuid = UUID.randomUUID();

    @BeforeAll
    static void setUp()
    {
        raceApplication = new RaceApplication();
        raceApplication.setId(uuid);
        raceApplication.setFirstName("First");
        raceApplication.setLastName("Last");
        raceApplication.setClub("Club");
        raceApplication.setDistance(Distance.TEN_K);
    }

    @Test
    void getId()
    {
        assertEquals(uuid, raceApplication.getId());
    }

    @Test
    void getFirstName()
    {
        assertEquals("First", raceApplication.getFirstName());
    }

    @Test
    void getLastName()
    {
        assertEquals("Last", raceApplication.getLastName());
    }

    @Test
    void getClub()
    {
        assertEquals("Club", raceApplication.getClub());
    }

    @Test
    void getDistance()
    {
        assertEquals(Distance.TEN_K, raceApplication.getDistance());
    }
}
