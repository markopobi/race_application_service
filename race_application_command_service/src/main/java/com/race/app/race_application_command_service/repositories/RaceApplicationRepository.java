package com.race.app.race_application_command_service.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.race.app.race_application_command_service.domain.RaceApplication;

public interface RaceApplicationRepository extends JpaRepository<RaceApplication, UUID>
{
}
