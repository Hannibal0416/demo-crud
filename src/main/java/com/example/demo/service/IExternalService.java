package com.example.demo.service;

import com.example.demo.controller.response.Response;
import com.example.demo.controller.response.TodoResponse;

public interface IExternalService {
  Response getTodo(String url, String id);
  Response retryTodo(String url, String id);
}
