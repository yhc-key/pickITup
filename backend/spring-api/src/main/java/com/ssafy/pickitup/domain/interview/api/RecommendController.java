package com.ssafy.pickitup.domain.interview.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/recommend")
public class RecommendController {

  private final WebClient webClient = WebClient.create("http://localhost:9000");

  @GetMapping
  public String recommend() {
    String result = webClient.get().uri("/api/test").retrieve().bodyToMono(String.class).block();
    return "result: " + result + " from recommend";
  }

  @GetMapping("/test")
  public String test(Integer userId) {
    webClient.get().uri("/api/recommend/normal/" + userId)
        .retrieve()
        .bodyToFlux(String.class)
        .subscribe(System.out::println);

    return "test";
  }
}
