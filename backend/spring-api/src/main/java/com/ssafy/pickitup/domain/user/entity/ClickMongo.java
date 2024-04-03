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
@Document(collection = "click")
public class ClickMongo {

    @Id
    private String id;

    private Integer userId;
    private Integer recruitId;
    private Integer clickCount;

    public static ClickMongo createClick(Integer userId, Integer recruitId) {
        return ClickMongo.builder()
            .userId(userId)
            .recruitId(recruitId)
            .clickCount(1)
            .build();
    }

    public void increaseClickCount() {
        this.clickCount++;
    }
}
