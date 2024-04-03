package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.user.query.dto.UserClickResponseDto;

public interface UserClickService {
    public UserClickResponseDto findAllUserClick(Integer authId);
}
