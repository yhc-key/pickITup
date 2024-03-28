package com.ssafy.pickitup.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Document(collection = "click")
public class ClickMongo {

    private Integer userId;
    private Integer recruitId;
    private Integer clickCount;

    public void increaseClickCount() {
        this.clickCount++;
    }
}
