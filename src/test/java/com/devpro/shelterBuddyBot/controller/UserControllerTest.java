package com.devpro.shelterBuddyBot.controller;

import com.devpro.shelterBuddyBot.ShelterBuddyBotApplication;
import com.devpro.shelterBuddyBot.model.ShelterClients;
import com.devpro.shelterBuddyBot.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {ShelterBuddyBotApplication.class})
@RequiredArgsConstructor
public class UserControllerTest {

    @MockBean
    private UserService userServiceMock;
    private ShelterClients user;
    private static Integer ID = 1;
    private static String PHONE_NUMBER = "+79053930303";
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final WebApplicationContext webApplicationContext;

    @BeforeEach
    void init() throws Exception {
        user = new ShelterClients();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

//    @Test
//    void shouldReturn200WhenUpdateCorrectFieldsUserById() throws Exception {
//        String json = objectMapper.writeValueAsString(user);
//        when(userServiceMock.editUser(any()));
//        mockMvc.perform(post("http://localhost:8080/user/" + ID)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(ID.toString())
//                        .content(json))
//                .andDo(print())
//                .andExpect(status().isOk());
//        verify(userServiceMock,times(1)).updateById(any(), any());
//    }

//    @Test
//    void shouldReturn200WhenFindAllUsers() throws Exception {
//        when(userServiceMock.findAll()).thenReturn(List.of(user));
//        mockMvc.perform(get("http://localhost:8080/user"))
//                .andDo(print())
//                .andExpect(status().isOk());
//        verify(userServiceMock).findAll();
//    }


}