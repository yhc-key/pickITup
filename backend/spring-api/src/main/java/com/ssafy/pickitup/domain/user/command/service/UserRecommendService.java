package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.user.dto.UserRecommendDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class UserRecommendService {


    private final WebClient webClient;

    @Autowired
    public UserRecommendService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://recommend.pickitup.online")
            .build();
    }

    public void sendSignalToScalaServerByKeywordChange() {

        // WebClient를 사용하여 비동기 요청 보내기
        Mono<String> responseMono = webClient.post()
            .uri("/api/similarity/all")
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);

        // Mono를 구독하고 응답을 처리
        responseMono.subscribe(
            response -> log.debug("스칼라 서버에서 받은 응답 = {} ", response),
            error -> log.debug("에러 발생 = {} ", error.getMessage()),
            () -> log.debug("응답 처리 완료")
        );
    }

    public void sendSignalToScalaServerByAddressChange() {

        // WebClient를 사용하여 비동기 요청 보내기
        Mono<String> responseMono = webClient.post()
            .uri("/api/distance/all")
            .contentType(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(String.class);

        // Mono를 구독하고 응답을 처리
        responseMono.subscribe(
            response -> log.debug("스칼라 서버에서 받은 응답 = {} ", response),
            error -> log.debug("에러 발생 = {} ", error.getMessage()),
            () -> log.debug("응답 처리 완료")
        );
    }

    @Cacheable(
        cacheNames = "recommend",
        key = "#userId"
    )
    public List<UserRecommendDto> getUserRecommendRecruitList(Integer userId, boolean isSuperUser) {
        // WebClient를 사용하여 동기 요청 보내기
        Flux<UserRecommendDto> response = webClient.get()
            .uri(
                "/api/recommend/" +
                    (isSuperUser ? "super" : "normal") +
                    "/{userId}",
                userId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(UserRecommendDto.class);

        return response.collectList().block();
    }
}
