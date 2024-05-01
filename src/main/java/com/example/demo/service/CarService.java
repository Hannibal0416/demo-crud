package com.example.demo.service;

import com.example.demo.entity.Car;
import com.example.demo.entity.User;
import com.example.demo.repository.CarRepository;
import com.example.demo.request.CarRequest;
import com.example.demo.response.CarResponse;
import com.example.demo.response.UserResponse;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CarService implements ICarService {
  @Autowired
  private CarRepository carRepository;

  @Override
  public CarResponse getById(Long id) {
    Car car = carRepository.findById(id)
        .orElseThrow(() -> new DataRetrievalFailureException("Car not found"));
    CarResponse carResponse = new CarResponse();
    BeanUtils.copyProperties(car, carResponse);
    return carResponse;
  }

  @Override
  public List<CarResponse> getAll(Integer offset, Integer limit) {
    Pageable pageable = PageRequest.of(offset, limit, Sort.by("id"));
    List<Car> cars = carRepository.findAll(pageable);
    return cars.stream().map(car -> {
      CarResponse carResponse = new CarResponse();
      BeanUtils.copyProperties(car, carResponse);
      if (car.getUser() != null) {
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(car.getUser(), userResponse);
        carResponse.setUser(userResponse);
      }
      return carResponse;
    }).collect(Collectors.toList());
  }

  @Override
  public void add(CarRequest req) {
    Car car = new Car();
    BeanUtils.copyProperties(req, car);
    carRepository.save(car);
  }

  @Override
  public void update(Long id, CarRequest carRequest) {

  }
  @Override
  @Transactional
  public void buyCar(Long userId, Long carId) {
    User user = new User();
    user.setId(userId);
    int cnt = carRepository.updateUserIdById(user, carId);
    if (cnt == 0) {
      throw new DataRetrievalFailureException("Car not found");
    }
  }
  @Override
  public void delete(Long id) {
    carRepository.deleteById(id);
  }
}
