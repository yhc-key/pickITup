package com.ssafy.pickitup.domain.company.entity;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@Document(collection = "company")
public class CompanyMongo {

    @Id
    private Integer id;

    private String name;
    private Float[] location;
    private Float latitude;
    private Float longitude;
    private int salary;
    private Set<Integer> recruits;
}