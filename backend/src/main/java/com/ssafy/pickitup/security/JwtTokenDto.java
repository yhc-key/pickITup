package com.ssafy.pickitup.security;

import java.util.HashMap;
import java.util.Map;


//@Getter
public class JwtTokenDto {

  private String accessToken;
  private String refreshToken;

  public JwtTokenDto(String accessToken, String refreshToken) {
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }

  public Map<String, Object> responseDto() {
    Map<String, Object> result = new HashMap<>();
    result.put(JwtProperties.TOKEN_TYPE, JwtProperties.TOKEN_PREFIX.substring(0, 6));
    result.put(JwtProperties.ACCESS_TOKEN, this.getAccessToken());
    result.put(JwtProperties.REFRESH_TOKEN, this.getRefreshToken());
    result.put(JwtProperties.EXPRIES_IN, JwtProperties.ACCESS_TOKEN_EXPIRATION_TIME);
    return result;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getRefreshToken() {
    return refreshToken;
  }
}
