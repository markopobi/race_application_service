package com.race.app.query.domain;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RaceApplicationRepository extends JpaRepository<RaceApplication, UUID>
{
}
