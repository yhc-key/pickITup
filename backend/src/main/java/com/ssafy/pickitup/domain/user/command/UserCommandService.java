package com.ssafy.pickitup.domain.user.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserCommandJpaRepository userCommandJpaRepository;


}
