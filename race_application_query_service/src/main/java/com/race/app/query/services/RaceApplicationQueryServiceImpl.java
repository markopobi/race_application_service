package com.race.app.query.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.race.app.query.domain.RaceApplication;
import com.race.app.query.domain.RaceApplicationEvent;
import com.race.app.query.domain.RaceApplicationRepository;
import com.race.app.query.exceptions.RaceApplicationNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RaceApplicationQueryServiceImpl implements RaceApplicationQueryService
{

    private final RaceApplicationRepository raceApplicationRepository;

    public RaceApplicationQueryServiceImpl(RaceApplicationRepository raceApplicationRepository)
    {
        this.raceApplicationRepository = raceApplicationRepository;
    }

    @Override
    public List<RaceApplication> getAllRaceApplications()
    {
        return raceApplicationRepository.findAll();
    }

    @Override
    public RaceApplication getRaceApplication(UUID id)
    {
        log.debug("Getting race application with id: " + id + " from repository.");
        Optional<RaceApplication> raceApplication = raceApplicationRepository.findById(id);
        return raceApplication.orElseThrow(
                () -> new RaceApplicationNotFoundException("Race application with ID: " + id + " not found"));
    }

    @Override
    public void consume(RaceApplicationEvent raceApplicationEvent)
    {
        log.debug("Consuming event type " + raceApplicationEvent.getType() + " for race application with id "
                + raceApplicationEvent.getRaceApplication().getId() + "in repository.");
        switch (raceApplicationEvent.getType())
        {
            case "CREATE", "PATCH" -> {
                RaceApplication newApplication = raceApplicationEvent.getRaceApplication();
                raceApplicationRepository.save(newApplication);
            }
            case "DELETE" -> {
                UUID idToDelete = raceApplicationEvent.getRaceApplication().getId();
                raceApplicationRepository.deleteById(idToDelete);
            }
            default -> throw new IllegalArgumentException("Invalid event type");
        }
    }
}
