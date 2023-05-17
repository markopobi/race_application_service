package com.race.app.race_application_command_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.race.app.race_application_command_service.auth.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsername(String username);
}