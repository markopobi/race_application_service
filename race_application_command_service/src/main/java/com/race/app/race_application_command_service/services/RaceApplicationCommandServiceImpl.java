package com.race.app.race_application_command_service.services;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.race.app.race_application_command_service.domain.Distance;
import com.race.app.race_application_command_service.domain.RaceApplication;
import com.race.app.race_application_command_service.domain.RaceApplicationEvent;
import com.race.app.race_application_command_service.exceptions.RaceAppException;
import com.race.app.race_application_command_service.exceptions.RaceApplicationNotFoundException;
import com.race.app.race_application_command_service.kafka.KafkaProducer;
import com.race.app.race_application_command_service.repositories.RaceApplicationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RaceApplicationCommandServiceImpl implements RaceApplicationCommandService
{

    private final RaceApplicationRepository raceApplicationRepository;

    private final KafkaProducer kafkaProducer;

    public RaceApplicationCommandServiceImpl(RaceApplicationRepository raceApplicationRepository,
            KafkaProducer kafkaProducer)
    {
        this.raceApplicationRepository = raceApplicationRepository;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public RaceApplication create(RaceApplication raceApplication)
    {
        if (raceApplicationRepository.findById(raceApplication.getId()).isPresent())
        {
            throw new RaceAppException(
                    "Race application with ID: " + raceApplication.getId() + " already exists!");
        }
        log.debug("Creating race application with id: " + raceApplication.getId() + " in repository.");
        RaceApplication createdRaceApplication = raceApplicationRepository.save(raceApplication);
        kafkaProducer.sendMessage(new RaceApplicationEvent("CREATE", createdRaceApplication));
        return createdRaceApplication;
    }

    @Override
    public RaceApplication patch(UUID id, Map<String, Object> updates) {
        log.debug("Patching race application with id: " + id + " in repository.");
        return raceApplicationRepository.findById(id)
                .map(raceApplication -> {
                    updates.forEach((k, v) -> {
                        switch (k)
                        {
                            case "firstName" -> raceApplication.setFirstName((String) v);
                            case "lastName" -> raceApplication.setLastName((String) v);
                            case "club" -> raceApplication.setClub((String) v);
                            case "distance" -> raceApplication.setDistance((Distance) v);
                            default -> {
                            }
                        }
                    });
                    RaceApplication patchedRaceApplication = raceApplicationRepository.save(raceApplication);
                    kafkaProducer.sendMessage(new RaceApplicationEvent("PATCH", patchedRaceApplication));
                    return patchedRaceApplication;
                })
                .orElseThrow(() -> new RaceAppException("Unable to patch application with ID: " + id));
    }

    @Override
    public void delete(RaceApplication existingRaceApplication)
    {
        try {
            log.debug("Deleting race application with id: " + existingRaceApplication.getId() + " in repository.");
            raceApplicationRepository.delete(existingRaceApplication);
            kafkaProducer.sendMessage(new RaceApplicationEvent("DELETE", existingRaceApplication));
        } catch (Exception e) {
            throw new RaceAppException("Could not delete application with ID: " + existingRaceApplication);
        }

    }

    @Override
    public RaceApplication findById(UUID id)
    {
        Optional<RaceApplication> raceApplicationOptional = raceApplicationRepository.findById(id);

        if (raceApplicationOptional.isEmpty())
        {
            throw new RaceApplicationNotFoundException("Race application not found for ID value: " + id);
        }
        return raceApplicationOptional.get();
    }
}
