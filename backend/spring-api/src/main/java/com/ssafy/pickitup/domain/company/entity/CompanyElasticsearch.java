package com.ssafy.pickitup.domain.company.entity;

import com.ssafy.pickitup.global.entity.GeoLocation;
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

    /*
        월급 int로 변환
     */
    private int convertSalaryIntoInt(String salary) {
        if (salary.isEmpty()) {
            return -1; // 연봉 정보가 없는 경우 -1 리턴
        } else {
            return Integer.parseInt(salary) * 10_000;
        }
    }

    public CompanyMongo toMongo(GeoLocation geoLocation) {
        return CompanyMongo.builder()
            .id(this.id)
            .name(this.name)
            .latitude(geoLocation != null ? geoLocation.getLatitude() : null)
            .longitude(geoLocation != null ? geoLocation.getLongitude() : null)
            .salary(convertSalaryIntoInt(this.salary))
            .recruits(new HashSet<>())
            .build();
    }
}
