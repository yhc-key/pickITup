package com.ssafy.pickitup.security.service;

import com.ssafy.pickitup.domain.auth.query.AuthQueryJpaRepository;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthQueryJpaRepository authQueryJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authQueryJpaRepository.findAuthByUsername(username)
            .map(this::createUserDetails)
            .orElseThrow(() -> new UsernameNotFoundException(
                "Can't find user with this username. -> " + username));
    }
    public UserDetails createUserDetails(com.ssafy.pickitup.domain.auth.entity.Auth auth) {
        return User.builder()
            .username(auth.getUsername())
            .password(auth.getPassword())
            .roles(auth.getRole().toString())
            .build();
    }
}
