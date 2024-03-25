package com.ssafy.pickitup.domain.user.entity;

import com.ssafy.pickitup.domain.user.query.dto.UserMongoQueryResponseDto;
import java.util.List;
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
@Document(collection = "user")
public class UserMongo {

    @Id
    private Integer id;

    private List<String> keywords;
    private double latitude;
    private double longitude;

    public UserMongoQueryResponseDto toQueryResponse() {
        return UserMongoQueryResponseDto.builder()
            .id(this.id)
            .keywords(this.keywords)
            .latitude(this.latitude)
            .longitude(this.longitude)
            .build();
    }
}