package com.example.demo.service;

import com.example.demo.request.CarRequest;
import com.example.demo.response.CarResponse;

public interface ICarService extends IService<CarRequest, CarResponse> {
  void buyCar(Long userId, Long carId);
}
