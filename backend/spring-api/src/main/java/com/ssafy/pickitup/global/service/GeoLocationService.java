package com.ssafy.pickitup.global.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.pickitup.global.entity.GeoLocation;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeoLocationService {

    private final String KAKAO_MAPS_GEOCODING_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    @Value("${kakao.api}")
    private String KAKAO_API_KEY;

    public GeoLocation getGeoLocation(String address) {
        if (address == null) {
            return new GeoLocation(0, 0);
        }
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url(KAKAO_MAPS_GEOCODING_API_URL + "?query=" + address)
            .addHeader("Authorization", KAKAO_API_KEY) // 카카오 REST API 키 입력
            .build();

        try {
            Response response = client.newCall(request).execute();
            System.out.println("response = " + response);
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(responseBody);
                JsonNode documents = jsonNode.path("documents");
                if (documents.isArray() && documents.size() > 0) {
                    double latitude = documents.get(0).path("y").asDouble();
                    double longitude = documents.get(0).path("x").asDouble();
                    return new GeoLocation(latitude, longitude);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
