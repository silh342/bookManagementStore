package com.younes.reskilingproject.bookManagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.younes.reskilingproject.bookManagement.dto.BookRequestBody;
import com.younes.reskilingproject.bookManagement.entity.Book;
import com.younes.reskilingproject.bookManagement.restController.BookController;
import com.younes.reskilingproject.bookManagement.service.BookService.ImplBookService;
import com.younes.reskilingproject.bookManagement.service.CategoryService.ImplCategoryService;
import com.younes.reskilingproject.bookManagement.service.InventoryService.ImplInventoryService;
import com.younes.reskilingproject.bookManagement.service.ReviewService.ReviewServiceImpl;
import com.younes.reskillingproject.userManagement.security.Service.UserServiceImpl;
import com.younes.reskillingproject.userManagement.security.jwtAuthentication.JwtGenerator;
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

import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


// disable spring security so we don't add token when testing controller api urls that need jwt bearer token
@WebMvcTest(controllers = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ImplBookService bookService;
    @MockBean
    private ImplInventoryService inventoryService;
    @MockBean
    private ImplCategoryService categoryService;
    @MockBean
    private ReviewServiceImpl reviewService;
    @MockBean
    private JwtGenerator  jwtGenerator;
    @MockBean
    private UserServiceImpl userService;
    @Autowired
    private ObjectMapper objectMapper;
    private BookRequestBody bookRequestBody;

    @Test
    public void BookController_addNewBook_ReturnCreatedBook() throws Exception {
       Book book = new Book("78954223", "Gluten free recipes", 100, null, null, new Date(), new Date(), null);

        given(bookService.saveBook(ArgumentMatchers.any())).willAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        ResultActions response = mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(book)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

}
