package com.ssafy.pickitup.domain.user.command.service;

import com.ssafy.pickitup.domain.user.dto.UserRecommendDto;
import com.ssafy.pickitup.domain.user.entity.User;
import com.ssafy.pickitup.domain.user.exception.UserNotFoundException;
import com.ssafy.pickitup.domain.user.query.UserQueryJpaRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserRecommendService {


    private final WebClient webClient;
    private final UserQueryJpaRepository userQueryJpaRepository;

    @Autowired
    public UserRecommendService(WebClient.Builder webClientBuilder,
        UserQueryJpaRepository userQueryJpaRepository) {
        this.webClient = webClientBuilder.baseUrl("https://recommend.pickitup.online")
            .build();
        this.userQueryJpaRepository = userQueryJpaRepository;
    }

    public void sendRequestToScalaServer() {
        // 요청 본문 데이터 - JSON 형식
        String requestBody = "{\"key\":\"value\"}";

        // WebClient를 사용하여 비동기 요청 보내기
        Mono<String> responseMono = webClient.get()
            .uri("/api/test")
            .retrieve()
            .bodyToMono(String.class);

        // Mono를 구독하고 응답을 처리
        responseMono.subscribe(
            response -> System.out.println("스칼라 서버에서 받은 응답: " + response),
            error -> System.err.println("에러 발생: " + error),
            () -> System.out.println("응답 처리 완료")
        );
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
            response -> System.out.println("스칼라 서버에서 받은 응답: " + response),
            error -> System.err.println("에러 발생: " + error),
            () -> System.out.println("응답 처리 완료")
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
            response -> System.out.println("스칼라 서버에서 받은 응답: " + response),
            error -> System.err.println("에러 발생: " + error),
            () -> System.out.println("응답 처리 완료")
        );
    }

//    public Mono<String> getUserRecommendRecruitList(Integer userId) {
//        // WebClient를 사용하여 비동기 요청 보내기
//        return webClient.get()
//            .uri("/api/recommend/normal/{userId}", userId)
//            .retrieve()
//            .bodyToMono(String.class)
//            .doOnSuccess(response -> log.info("스칼라 서버에서 받은 응답 = {}", response))
//            .doOnError(error -> log.error("에러 발생 = {}", error))
//            .doFinally(signal -> log.info("응답 처리 완료"));
//    }

    public List<UserRecommendDto> getUserRecommendRecruitList(Integer userId) {

        //user가 기술스택과  address를 입력했을 때만 추천 서비스를 이용할 수 있으므로 validation check
        User user = userQueryJpaRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        log.info("user = {}", user.toString());
        if (user.getAddress() == null || user.getAddress().length() == 0) {
            log.info("user address 가 없습니다.");
            return null;
        }

        if (user.getUserKeywords() == null || user.getUserKeywords().size() == 0) {
            log.info("user keywords 가 없습니다. = {}");
            return null;
        }

        // WebClient를 사용하여 동기 요청 보내기
        Flux<UserRecommendDto> response = webClient.get()
            .uri("/api/recommend/normal/{userId}", userId)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(UserRecommendDto.class);

        return response.collectList().block();
    }
}
