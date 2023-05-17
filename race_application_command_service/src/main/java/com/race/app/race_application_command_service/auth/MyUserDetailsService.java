package com.race.app.race_application_command_service.auth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("UserEntity not found with username: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();

        // Add roles as authorities
        authorities.addAll(
                Arrays.stream(userEntity.getRoles().split(","))
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.trim()))
                        .collect(Collectors.toList())
        );

        // Add extra authorities
        authorities.addAll(
                Arrays.stream(userEntity.getAuthorities().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList())
        );

        return new User(userEntity.getUsername(), userEntity.getPassword(), authorities);
    }
}
