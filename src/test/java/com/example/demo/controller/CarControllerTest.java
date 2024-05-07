package com.example.demo.controller;

import com.example.demo.domain.Car;
import com.example.demo.domain.User;
import com.example.demo.repository.CarRepository;
import com.example.demo.controller.response.CarResponse;
import com.example.demo.controller.response.UserResponse;
import com.example.demo.service.CarService;
import com.example.demo.service.ICarService;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisabledInAotMode
class CarControllerTest {

  static List<CarResponse> mockCars;
  @Autowired private MockMvc mockMvc;

  @MockBean private ICarService mockCarService;

  @BeforeAll
  static void before() {
    UserResponse user = new UserResponse(1L, "u1", 1, new ArrayList<>());
    mockCars =
        new ArrayList<>(
            ImmutableList.of(
                new CarResponse(1L, "model 1", user), new CarResponse(2L, "model 2", user)));
  }

  @Test
  void getCars() throws Exception {

    // makes a real method call
    Mockito.when(mockCarService.getAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockCars);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/cars?offset=0&limit=10").accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    String expected =
        """
                [
                  {
                    "id": 1,
                    "model": "model 1",
                    "user": {
                      "id": 1,
                      "name": "u1",
                      "age": 1,
                      "cars":[]
                    }
                  },
                  {
                    "id": 2,
                    "model": "model 2",
                    "user": {
                      "id": 1,
                      "name": "u1",
                      "age": 1,
                      "cars":[]
                    }
                  }
                ]
                """;
    JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
  }

}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisabledInAotMode
class SpyCarControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private CarRepository spyCarRepository;

  @Test
  void getCarById() throws Exception {
    // does not call the method at all.
    Mockito.doReturn(Optional.of(new Car(1L,"model 1", new User()))).when(spyCarRepository).findById(Mockito.anyLong());

    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/cars/1").accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    String expected =
        """
        {
          "id": 1,
          "model": "model 1"
        }
        """;
    JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
  }
}
