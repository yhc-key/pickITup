package com.ssafy.pickitup.user.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser(username = "1", roles = {"ADMIN"})
    @DisplayName("1-1 유저 조회 - 성공")
    void test1_1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/me"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(jsonPath("$.success", is(true)));
    }

}
