package com.race.app.race_application_command_service.auth;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.race.app.race_application_command_service.repositories.UserRepository;

public class MyUserDetailsServiceTest {

    private MyUserDetailsService myUserDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        myUserDetailsService = new MyUserDetailsService(userRepository);
    }

    @Test
    public void loadUserByUsername_WhenUserExists_ReturnsUserDetails() {
        //given
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setPassword("password");
        userEntity.setRoles("ADMIN,USER");
        userEntity.setAuthorities("race_application:write,race_application:patch,race_application:delete");

        when(userRepository.findByUsername("test")).thenReturn(java.util.Optional.of(userEntity));

        //when
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("test");

        //then
        assertEquals(userEntity.getUsername(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
    }

    @Test
    public void loadUserByUsername_WhenUserDoesNotExist_ThrowsUsernameNotFoundException() {
        //given
        when(userRepository.findByUsername("test")).thenReturn(Optional.empty());

        //when and then
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailsService.loadUserByUsername("test"));
    }
}
