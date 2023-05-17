package com.race.app.race_application_query_service.domain;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RaceApplicationEventTest
{

    private static RaceApplicationEvent raceApplicationEvent;
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

        raceApplicationEvent = new RaceApplicationEvent("CREATE", raceApplication);
    }

    @Test
    void getType()
    {
        assertEquals("CREATE", raceApplicationEvent.getType());
    }

    @Test
    void getRaceApplication()
    {
        assertEquals(raceApplication, raceApplicationEvent.getRaceApplication());
    }
}