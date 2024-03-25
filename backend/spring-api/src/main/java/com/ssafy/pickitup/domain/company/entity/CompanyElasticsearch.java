package com.ssafy.pickitup.domain.company.entity;

import java.util.HashSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;


@Document(indexName = "searchcompany")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyElasticsearch {


    @Transient
    private final String KAKAO_MAPS_GEOCODING_API_URL = "https://dapi.kakao.com/v2/local/search/address.json";
    @Id
    private Integer id;
    private String name;
    private String address;
    private String salary;
    @Transient
    @Value("${kakao.api}")
    private String KAKAO_API_KEY;


    private static Float[] convertAddressToLongitudeAndLatitude(String address) {
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
        // 월급 int로 변환
        return 0;
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
