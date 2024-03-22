package com.ssafy.pickitup.domain.company.entity;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document(collection = "company")
public class CompanyMongo {

    @Id
    private Integer id;

    private String name;
    private int latitude;
    private int longitude;
    private int salary;
    private Set<Integer> recruits;
}