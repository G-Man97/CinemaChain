package com.gman97.cinemachain.mapper;

import com.gman97.cinemachain.dto.UserCreateDto;
import com.gman97.cinemachain.entity.Role;
import com.gman97.cinemachain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User> {

    private final PasswordEncoder passwordEncoder;

    @Override
    public User map(UserCreateDto object) {
        return new User(
                null,
                object.getUsername(),
                passwordEncoder.encode(object.getRowPassword()),
                Role.USER
        );
    }
}
