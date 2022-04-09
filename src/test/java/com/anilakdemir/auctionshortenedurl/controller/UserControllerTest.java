package com.anilakdemir.auctionshortenedurl.controller;

import com.anilakdemir.auctionshortenedurl.dto.ShortUrlSaveRequestDto;
import com.anilakdemir.auctionshortenedurl.dto.UserSaveRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author anilakdemir
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UserControllerTest extends BaseTest{

    private static final String BASE_PATH = "/user";

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void shouldSignUp() throws Exception{

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUsername("testSaveUsername");
        userSaveRequestDto.setPassword("testSavePassword");

        String content = objectMapper.writeValueAsString(userSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH + "/signup").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotSignUp_whenUsernameIsAlreadyExists() throws Exception{

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUsername("testExistsUsername");
        userSaveRequestDto.setPassword("testExistsPassword");

        String content = objectMapper.writeValueAsString(userSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH + "/signup").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isConflict()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotSignUp_whenUsernameParameterIsNull() throws Exception{

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUsername(null);
        userSaveRequestDto.setPassword("testParameterIsNull");

        String content = objectMapper.writeValueAsString(userSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH + "/signup").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotSignUp_whenPasswordParameterIsNull() throws Exception{

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUsername("testParameterIsNull");
        userSaveRequestDto.setPassword(null);

        String content = objectMapper.writeValueAsString(userSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH + "/signup").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotSignUp_whenUsernameParameterIsBlank() throws Exception{

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUsername("  ");
        userSaveRequestDto.setPassword("testBlankUsername");

        String content = objectMapper.writeValueAsString(userSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH + "/signup").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotSignUp_whenPasswordParameterIsBlank() throws Exception{

        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();
        userSaveRequestDto.setUsername("testBlankPassword");
        userSaveRequestDto.setPassword("  ");

        String content = objectMapper.writeValueAsString(userSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH + "/signup").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldCreateShortUrl() throws Exception{

        Long userId = 1L;

        ShortUrlSaveRequestDto shortUrlSaveRequestDto = new ShortUrlSaveRequestDto();

        String content = objectMapper.writeValueAsString(shortUrlSaveRequestDto);

        MvcResult result = mockMvc.perform(
                post(BASE_PATH + "/" + userId + "/url/create").content(content).contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldGetAllUrlListByUserId() throws Exception{

        Long userId = 1L;

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/" + userId + "/url/list").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotGetAllUrlListByUserId_whenUserNotFound() throws Exception{

        Long userId = -1L;

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/" + userId + "/url/list").content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldGetUrlDetailByUserId() throws Exception{

        Long userId = 1L;
        Long urlId = 1L;

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/" + userId + "/url/detail/" + urlId).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotGetUrlDetailByUserId_whenUserNotFound() throws Exception{

        Long userId = -1L;
        Long urlId = 1L;

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/" + userId + "/url/detail/" + urlId).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotGetUrlDetailByUserId_whenUrlNotFound() throws Exception{

        Long userId = 1L;
        Long urlId = -1L;

        MvcResult result = mockMvc.perform(
                get(BASE_PATH + "/" + userId + "/url/detail/" + urlId).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldDeleteUrlByIdAndUserId() throws Exception{

        Long userId = 2L;
        Long urlId = 2L;

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/" + userId + "/url/detail/" + urlId).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertTrue(isSuccess);
    }

    @Test
    void shouldNotDeleteUrlByIdAndUserId_whenUserNotFound() throws Exception{

        Long userId = -2L;
        Long urlId = 2L;

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/" + userId + "/url/detail/" + urlId).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

    @Test
    void shouldNotDeleteUrlByIdAndUserId_whenUrlNotFound() throws Exception{

        Long userId = 2L;
        Long urlId = -2L;

        MvcResult result = mockMvc.perform(
                delete(BASE_PATH + "/" + userId + "/url/detail/" + urlId).content("").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

        boolean isSuccess = isSuccess(result);

        assertFalse(isSuccess);
    }

}