package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.request.UserRequest;
import com.example.demo.response.CarResponse;
import com.example.demo.response.UserResponse;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService implements IService<UserRequest, UserResponse> {


  @Autowired
  private UserRepository userRepository;

  @Override
  public UserResponse getById(Long id) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new DataRetrievalFailureException("User not found"));
    UserResponse userResponse = new UserResponse();
    BeanUtils.copyProperties(user, userResponse);
    if (!user.getCars().isEmpty()) {
      List<CarResponse> carResponses = user.getCars().stream().map(car -> {
        CarResponse carResponse = new CarResponse();
        BeanUtils.copyProperties(car, carResponse);
        return carResponse;
      }).toList();
      userResponse.setCars(carResponses);
    }
    return userResponse;
  }

  @Override
  public List<UserResponse> getAll(Integer offset, Integer limit) {
    Pageable pageable = PageRequest.of(offset, limit, Sort.by("id"));
    Iterable<User> users = userRepository.findAll(pageable);
    return StreamSupport.stream(users.spliterator(), false).map(user -> {
      UserResponse userResponse = new UserResponse();
      BeanUtils.copyProperties(user, userResponse);
      if (!user.getCars().isEmpty()) {
        List<CarResponse> carResponses = user.getCars().stream().map(car -> {
          CarResponse carResponse = new CarResponse();
          BeanUtils.copyProperties(car, carResponse);
          return carResponse;
        }).toList();
        userResponse.setCars(carResponses);
      }
      return userResponse;
    }).toList();
  }

  @Override
  public void add(UserRequest req) {
    User user = new User();
    BeanUtils.copyProperties(req, user);
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void update(Long id, UserRequest req) {
    User user = new User();
    BeanUtils.copyProperties(req, user);
    user.setId(id);
    int cnt = userRepository.updateUserById(user);
    if (cnt == 0) {
      throw new DataRetrievalFailureException("User not found");
    }

  }


  @Override
  public void delete(Long id) {
    userRepository.deleteById(id);
  }
}
