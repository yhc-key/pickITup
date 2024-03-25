package com.ssafy.pickitup.domain.company.entity;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.code.geocoder.model.GeocoderStatus;
import com.google.code.geocoder.model.LatLng;
import java.io.IOException;
import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "searchcompany")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyElasticsearch {

    @Id
    private Integer id;

    private String name;
    private String address;
    private String salary;

    private static Float[] convertAddressToLongitudeAndLatitude(String address) {
        Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder()
            .setAddress(address)
            .setLanguage("ko")
            .getGeocoderRequest();

        System.out.println("address = " + address);

        try {
            GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
            System.out.println("geocoderResponse.getResults() = " + geocoderResponse.getResults());
            System.out.println("geocoderResponse.getStatus() = " + geocoderResponse.getStatus());

            if (geocoderResponse.getStatus() == GeocoderStatus.OK && !geocoderResponse.getResults()
                .isEmpty()) {
                GeocoderResult geocoderResult = geocoderResponse.getResults().get(0);
                LatLng location = geocoderResult.getGeometry().getLocation();
                System.out.println("location = " + location);
                System.out.println("float = " + new Float[]{location.getLat().floatValue(),
                    location.getLng().floatValue()});

                return new Float[]{location.getLat().floatValue(), location.getLng().floatValue()};
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private static int convertAddressToLatitude(String address) {
        // 위도 변환
        return 0;
    }

    private static int convertAddressToLongitude(String address) {
        // 경도 변환
        return 0;
    }

    private static int convertSalaryIntoInt(String salary) {
        if (salary.isEmpty()) {
            return 40_000_000;
        }
        // 월급 int로 변환
        else {
            return Integer.parseInt(salary) * 10_000;
        }
    }

    public CompanyMongo toMongo() {
        return CompanyMongo.builder()
            .id(this.id)
            .name(this.name)
            .location(convertAddressToLongitudeAndLatitude(this.address))
//            .latitude(convertAddressToLatitude(this.address))
//            .longitude(convertAddressToLongitude(this.address))
            .salary(convertSalaryIntoInt(this.salary))
            .recruits(new HashSet<>())
            .build();
    }
}
