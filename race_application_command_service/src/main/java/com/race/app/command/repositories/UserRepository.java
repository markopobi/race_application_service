package com.race.app.command.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.race.app.command.auth.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    UserEntity findByUsername(String username);
}