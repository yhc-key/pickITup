package com.ssafy.pickitup.user.controller;

import com.ssafy.pickitup.domain.user.command.repository.UserClickCommandJpaRepository;
import com.ssafy.pickitup.domain.user.entity.UserClick;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserClickControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserClickCommandJpaRepository userClickCommandJpaRepository;

    @Test
    @WithMockUser(username = "1", roles = {"ADMIN"})
    @DisplayName("1-1 유저 클릭 채용 공고 조회 - 성공")
    void test1_1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/click/recruit"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    @WithMockUser(username = "1", roles = {"ADMIN"})
    @DisplayName("1-2 유저 클릭 채용 공고 클릭 - 횟수 증가")
    void test1_2() throws Exception {

        Optional<UserClick> userIdAndRecruitIdBefore = userClickCommandJpaRepository.findByUserIdAndRecruitId(1, 1);
        int countBefore = userIdAndRecruitIdBefore.map(UserClick::getClickCount).orElse(0);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/click/recruit?recruitId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success", is(true)));

        Optional<UserClick> userIdAndRecruitIdAfter = userClickCommandJpaRepository.findByUserIdAndRecruitId(1, 1);
        int countAfter = userIdAndRecruitIdAfter.map(UserClick::getClickCount).orElse(0);

        assertThat(countAfter).isEqualTo(countBefore+1);
    }

}
