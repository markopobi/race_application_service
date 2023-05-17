package com.race.app.race_application_command_service.auth;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.race.app.race_application_command_service.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService
{

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity == null)
        {
            throw new UsernameNotFoundException("UserEntity not found with username: " + username);
        }
        return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
    }
}
