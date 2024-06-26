package com.example.demo.controller;

import com.example.demo.controller.request.UserRequest;
import com.example.demo.controller.response.UserResponse;
import java.util.*;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerTest {

  @LocalServerPort private int port;

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void getUsers() {
    String url = "http://localhost:" + port + "/users";
    ResponseEntity<UserResponse[]> response =
        this.restTemplate.getForEntity(url, UserResponse[].class);
    MatcherAssert.assertThat("users", Objects.requireNonNull(response.getBody()).length, Matchers.greaterThan(1));
  }

  @Test
  void getUserById() {
    String url = "http://localhost:" + port + "/users/100";
    ResponseEntity<UserResponse> response = this.restTemplate.getForEntity(url, UserResponse.class);
    Assertions.assertEquals(
        100, Objects.requireNonNull(Objects.requireNonNull(response.getBody()).getId()));
  }

  @Test
  void postUserBadRequest() {
    String url = "http://localhost:" + port + "/users";
    //    List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
    // Add the Jackson Message converter
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

    // Note: here we are making this converter to process any kind of response,
    // not only application/*json, which is the default behaviour
    converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    //    messageConverters.add(converter);
    restTemplate.getRestTemplate().getMessageConverters().add(converter);
    HttpEntity<UserRequest> request = new HttpEntity<>(new UserRequest("TestUser", 1));
    ResponseEntity<Void> response =
        this.restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void postUserSuccess() {
    String url = "http://localhost:" + port + "/users";
    HttpEntity<UserRequest> request = new HttpEntity<>(new UserRequest("TestUser", 19));
    ResponseEntity<Void> response =
        this.restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
    System.out.println(response);
    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }
}
