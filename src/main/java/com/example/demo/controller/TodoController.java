package com.example.demo.controller;

import com.example.demo.controller.request.TodoRequest;
import com.example.demo.controller.response.Response;
import com.example.demo.controller.response.TodoResponse;
import com.example.demo.service.IExternalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("todo")
public class TodoController {

  private final IExternalService externalService;

  @GetMapping("get")
  public Response getTodo(@RequestBody TodoRequest request) {
    return externalService.getTodo(request.getUrl(), request.getId());
  }

  @GetMapping("retry")
  public Response retry(@RequestBody TodoRequest request) {
    return externalService.retryTodo(request.getUrl(), request.getId());
  }

}
