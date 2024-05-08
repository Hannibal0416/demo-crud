package com.example.demo.service;

import com.example.demo.controller.response.Response;
import com.example.demo.controller.response.TodoResponse;
import com.google.errorprone.annotations.Keep;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
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
  @Retry(name = "retry-todo")
  public Response getTodo(String url, String id) {
    log.info("todo-service url {}, id {}", url,id);
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
  private Response fallbackMethod(Exception e) {
    log.info("err {}",e.getMessage());
    return new TodoResponse();
  }


  @Override
  @Retry(name = "retry-todo", fallbackMethod = "retryMethod")
  public Response retryTodo(String url, String id) {
    log.info("todo: url {}, id {}", url,id);
    if (id.equals("100")) {
      throw new DataRetrievalFailureException("not found");
    }
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    HttpEntity<TodoResponse> entity = new HttpEntity<>(null, headers);
    ResponseEntity<TodoResponse> response = restTemplate.exchange(
        (url + id), HttpMethod.GET, entity,
        TodoResponse.class);
    return response.getBody();
  }

  @Keep
  private Response retryMethod(String url, String id,Exception exception) {
    log.info("retry todo: url {}, id {}, ex {}", url,id, exception.getMessage());
    return new TodoResponse();
  }




}

