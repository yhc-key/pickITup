package com.ssafy.pickitup.domain.company.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyElasticsearchTest {


    private final String KAKAO_MAPS_GEOCODING_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    @Value("${kakao.api}")
    private String KAKAO_API_KEY;

    @Test
    void addressTest() {
        getGeocode("인천시 남동대로 860");
    }

    public String getGeocode(String address) {
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
                    System.out.println("longitude = " + longitude);
                    System.out.println("latitude = " + latitude);
                    return "Latitude: " + latitude + ", Longitude: " + longitude;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed to get geocode for the address.";
    }
}