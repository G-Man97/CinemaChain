package com.gman97.cinemachain.service;

import com.gman97.cinemachain.dto.UserCreateDto;
import com.gman97.cinemachain.mapper.UserCreateMapper;
import com.gman97.cinemachain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.gman97.cinemachain.entity.User;
import org.hibernate.HibernateException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    @Transactional
    public void create(UserCreateDto userCreateDto) {
        var user = userCreateMapper.map(userCreateDto);
        userRepository.save(user);
    }
}
