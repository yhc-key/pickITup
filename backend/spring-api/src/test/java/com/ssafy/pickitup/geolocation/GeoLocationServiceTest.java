package com.ssafy.pickitup.geolocation;

import com.ssafy.pickitup.global.entity.GeoLocation;
import com.ssafy.pickitup.global.service.GeoLocationService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class GeoLocationServiceTest {

    @Autowired
    private GeoLocationService geoLocationService;
    @Test
    @DisplayName("주소 변환 성공1-1")
    public void changeAddressTest1_1() {
        GeoLocation geoLocation = geoLocationService.getGeoLocation(
            "세종특별자치시 소담동");
        assertNotNull(geoLocation, "주소 변환 결과가 null 이 아닌지 확인");
    }
    @Test
    @DisplayName("주소 변환 성공1-2")
    public void changeAddressTest1_2() {
        GeoLocation geoLocation = geoLocationService.getGeoLocation(
                "현대캐피탈본사(서울 강남구 역삼동)");
        assertNotNull(geoLocation, "주소 변환 결과가 null 이 아닌지 확인");
    }
}
