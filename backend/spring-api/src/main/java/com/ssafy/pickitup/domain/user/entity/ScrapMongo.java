package com.ssafy.pickitup.domain.user.entity;

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
@Document(collection = "scrap")
public class ScrapMongo {

    @Id
    private String id;

    private Integer userId;
    private Integer recruitId;

    public static ScrapMongo createScrap(Integer userId, Integer recruitId) {
        return ScrapMongo.builder()
            .userId(userId)
            .recruitId(recruitId)
            .build();
    }
}
