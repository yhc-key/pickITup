package com.ssafy.pickitup.domain.auth.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthApiResponse<T> {

    private T data;
}
