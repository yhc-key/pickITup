package com.ssafy.pickitup.domain.company.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CompanyElasticsearchTest {


    @Test
    @DisplayName("주소 변환 테스트")
    void addressToXY() {
        CompanyElasticsearch companyElasticsearch = new CompanyElasticsearch(1, "삼성", "강남구 삼성동 역삼동",
            "1000000");
        CompanyMongo mongo = companyElasticsearch.toMongo();
        System.out.println("mongo = " + mongo);

    }
}