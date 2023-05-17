package com.race.app.race_application_query_service.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceApplicationRepository extends JpaRepository<RaceApplication, UUID>
{
}
