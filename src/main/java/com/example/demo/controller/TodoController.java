package com.example.demo.controller;

import com.example.demo.controller.request.TodoRequest;
import com.example.demo.controller.response.Response;
import com.example.demo.controller.response.TodoResponse;
import com.example.demo.service.IExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TodoController {

  private final IExternalService externalService;

  @GetMapping("todo")
  public Response getTodo(@RequestBody TodoRequest request) {
    return externalService.getTodo(request.getUrl(), request.getId());
  }
}
