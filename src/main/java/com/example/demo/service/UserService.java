package com.example.demo.service;

import com.example.demo.dao.Car;
import com.example.demo.dao.User;
import com.example.demo.request.UserRequest;
import com.example.demo.response.CarResponse;
import com.example.demo.response.UserResponse;
import jakarta.transaction.Transactional;

import org.checkerframework.checker.units.qual.C;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public UserResponse getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new DataRetrievalFailureException("User not found"));
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

    public List<UserResponse> getUsers(Integer offset, Integer limit) {
        Pageable pageable = PageRequest.of(offset, limit, Sort.by("id"));
        Iterable<User> users = userRepository.findAll(pageable);
        return StreamSupport.stream(users.spliterator(), false).map( user -> {
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
            return  userResponse;
        }).toList();
    }

    public void addUser(UserRequest req) {
        User user = new User();
        BeanUtils.copyProperties(req,user);
        userRepository.save(user);
    }

    public void updateUser(Integer id, UserRequest req) {
        User user = new User();
        BeanUtils.copyProperties(req, user);
        user.setId(id);
        int cnt = userRepository.updateUserById(user);
        if (cnt == 0) {
            throw new DataRetrievalFailureException("User not found");
        }

    }


    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
