package com.example.demo.controller;



import com.example.demo.response.CarResponse;
import com.example.demo.response.UserResponse;
import com.example.demo.service.CarService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class CarControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CarService carService;

  static List<CarResponse> mockCars;

  @BeforeAll
  static void before() {
    UserResponse user = new UserResponse(1L, "u1", 1, null);
    mockCars = new ArrayList<>() {
      {
        add(new CarResponse(1L, "model 1", user));
        add(new CarResponse(2L, "model 2", user));
      }
    };
  }

  @Test
  void getCars() throws Exception {
    Mockito.when(carService.getAll(Mockito.anyInt(), Mockito.anyInt())).thenReturn(mockCars);
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/cars").accept(MediaType.APPLICATION_JSON);
    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    String expected = """
        [
          {
            "id": 1,
            "model": "model 1",
            "user": {
              "id": 1,
              "name": "u1",
              "age": 1
            }
          },
          {
            "id": 2,
            "model": "model 2",
            "user": {
              "id": 1,
              "name": "u1",
              "age": 1
            }
          }
        ]
        """;
    JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);

  }
}
