package com.race.app.command.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.race.app.command.repositories.UserRepository;

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

        when(userRepository.findByUsername("test")).thenReturn(userEntity);

        //when
        UserDetails userDetails = myUserDetailsService.loadUserByUsername("test");

        //then
        assertEquals(userEntity.getUsername(), userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
    }

    @Test
    public void loadUserByUsername_WhenUserDoesNotExist_ThrowsUsernameNotFoundException() {
        //given
        when(userRepository.findByUsername("test")).thenReturn(null);

        //when and then
        assertThrows(UsernameNotFoundException.class, () -> myUserDetailsService.loadUserByUsername("test"));
    }
}
