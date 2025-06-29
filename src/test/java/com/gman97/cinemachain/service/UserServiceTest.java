package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.UserCreateDto;
import com.gman97.cinemachain.entity.Role;
import com.gman97.cinemachain.entity.User;
import com.gman97.cinemachain.mapper.UserCreateMapper;
import com.gman97.cinemachain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserCreateMapper userCreateMapper;

    private final String USERNAME = "test@gmail.com";

    @Test
    void loadUserByUsername() {
        var user = new User(2L, USERNAME, "{encoded}testPassword", Role.USER);

        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(user));

        var actual = userService.loadUserByUsername(USERNAME);

        assertNotNull(actual);
        verify(userRepository).findByUsername(USERNAME);
    }

    @Test
    void loadUserByUsernameShouldThrowUsernameNotFoundException_whenSuchUserNotFound() {
        when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(USERNAME));
        verify(userRepository).findByUsername(USERNAME);
    }

    @Test
    void create() {
        var dto = new UserCreateDto(USERNAME, "rowTestPassword");
        var user = new User();

        when(userCreateMapper.map(dto)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        userService.create(dto);
        verify(userCreateMapper).map(dto);
        verify(userRepository).save(user);
    }
}