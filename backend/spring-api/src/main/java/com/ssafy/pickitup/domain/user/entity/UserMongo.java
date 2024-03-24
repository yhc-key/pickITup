package com.ssafy.pickitup.domain.user.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
@Document(collection = "user")
public class UserMongo {
}
