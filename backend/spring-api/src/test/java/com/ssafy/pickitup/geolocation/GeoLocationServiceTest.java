package com.ssafy.pickitup.geolocation;

import com.ssafy.pickitup.global.entity.GeoLocation;
import com.ssafy.pickitup.global.service.GeoLocationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GeoLocationServiceTest {

    @Autowired
    private GeoLocationService geoLocationService;

    @Test
    public void changeAddressTest() {

        GeoLocation geoLocation = geoLocationService.getGeoLocation(
            "세종특별자치시 소담동");
        System.out.println("geoLocation = " + geoLocation);
    }
}
