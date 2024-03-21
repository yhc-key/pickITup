package com.ssafy.pickitup.domain.company.entity;

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
            .latitude(convertAddressToLatitude(this.address))
            .longitude(convertAddressToLongitude(this.address))
            .salary(convertSalaryIntoInt(this.salary))
            .build();
    }
}
