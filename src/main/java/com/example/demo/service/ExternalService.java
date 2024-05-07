package com.example.demo.service;

import com.example.demo.controller.response.Response;
import com.example.demo.controller.response.TodoResponse;
import com.google.errorprone.annotations.Keep;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ExternalService implements IExternalService{

  private final RestTemplate restTemplate;
//  private final String ADDRESS_SERVICE_URL = "https://jsonplaceholder.typicode.com/todos/";

  @Override
  @CircuitBreaker(name = "todo-service", fallbackMethod = "fallbackMethod")
  public Response getTodo(String url, String id) {
    log.info("url {}, id {}", url,id);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<TodoResponse> entity = new HttpEntity<>(null, headers);
    ResponseEntity<TodoResponse> response = restTemplate.exchange(
        (url + id), HttpMethod.GET, entity,
        TodoResponse.class);
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      log.error("err",e);
    }
    return response.getBody();
  }


  @Keep
  private Response fallbackMethod(Exception exception) {
    return new TodoResponse();
  }


}

