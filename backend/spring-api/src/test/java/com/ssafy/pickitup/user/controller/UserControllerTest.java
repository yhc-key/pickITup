package com.ssafy.pickitup.user.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ssafy.pickitup.domain.user.api.UserController;
import com.ssafy.pickitup.domain.user.command.service.UserCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserCommandService userCommandService;

    @Test
    @WithMockUser(username = "1", roles = {"ADMIN"})
    @DisplayName("1-1 유저 조회 - 성공")
    void test1_1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/me"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)));
    }

    @Test
    @WithMockUser(username = "1", roles = {"ADMIN"})
    @DisplayName("1-2 유저 닉네임 변경 - 성공")
    public void test1_2() throws Exception {
        // Given
        mockMvc.perform(MockMvcRequestBuilders.patch("/users/nickname")
                        .content("""
                                "new nickname"
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)));
    }

}
