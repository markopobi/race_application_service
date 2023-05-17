package com.race.app.command.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.race.app.command.domain.RaceApplication;

public interface RaceApplicationRepository extends JpaRepository<RaceApplication, UUID>
{
}
