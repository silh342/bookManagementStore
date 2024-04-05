package com.younes.reskilingproject.bookManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.younes.reskilingproject.bookManagement.entity.Author;
import com.younes.reskilingproject.bookManagement.restController.AuthorController;
import com.younes.reskilingproject.bookManagement.service.AuthorService.ImplAuthorService;
import com.younes.reskillingproject.userManagement.security.Service.UserServiceImpl;
import com.younes.reskillingproject.userManagement.security.jwtAuthentication.JwtGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = AuthorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ImplAuthorService authorService;
    @MockBean
    private JwtGenerator jwtGenerator;
    @MockBean
    private UserServiceImpl userService;
    private Author author;

    @BeforeEach
    public void init() {
        author = new Author("Timothee Chalamet", "Young french handsome man", new HashSet<>());
    }

    @Test
    public void AuthorController_CreateAuthor_ReturnCreatedAuthor() throws Exception{
        given(authorService.saveAuthor(ArgumentMatchers.any())).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        ResultActions resultActions = mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(author)));

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
