package com.example.demo.controller

import com.example.demo.controller.response.CarResponse
import com.example.demo.controller.response.UserResponse
import com.example.demo.domain.Car
import com.example.demo.domain.User
import com.example.demo.repository.CarRepository
import com.example.demo.service.ICarService
import groovy.json.JsonBuilder
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.aot.DisabledInAotMode
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisabledInAotMode
class CarControllerSpokeTest extends Specification {
    @Autowired
    MockMvc mockMvc

    @SpringBean
    ICarService mockCarService = Mock()

    @Shared
    List<CarResponse> mockCars

    void setupSpec() {
        def user = new UserResponse(1L, "u1", 1, [])
        mockCars = [new CarResponse(1L, "model 1", user), new CarResponse(2L, "model 2", user)]
    }

    def "when 'getCar' is performed then the response has a 200 status"() {
        given: "a mocked car service with two mocked car responses"
        mockCarService.getAll(_ as Integer, _ as Integer) >> mockCars
        def requestBuilder = MockMvcRequestBuilders.get("/cars?offset=0&limit=10").accept(MediaType.APPLICATION_JSON)

        and: "expected json object with mocked responses"
        def json = new JsonBuilder(mockCars)

        when:
        def result = mockMvc.perform(requestBuilder).andReturn()

        then:
        verifyAll(result) {
            response.contentAsString == json.toString()
            response.status == HttpStatus.OK.value()
        }

    }

}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@DisabledInAotMode
class StubCarControllerSpokeTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @SpringBean
    CarRepository stubCarRepository = Stub {
        findAll(_) >> mockCars
    }

    @Shared
    List<Car> mockCars

    void setupSpec() {
        def user = new User(1L, "u1", 1, [])
        mockCars = [new Car(1L, "model 1", user), new Car(2L, "model 2", user)]
    }

    def "when 'getCar' is performed then the response has a 200 status"() {
        given: "a mocked car repository with two mocked cars"
        def requestBuilder = MockMvcRequestBuilders.get("/cars?offset=0&limit=10").accept(MediaType.APPLICATION_JSON)
        and:
        def userResponse = new UserResponse(1L, "u1", 1, [])
        def carResponse = [new CarResponse(1L, "model 1", userResponse), new CarResponse(2L, "model 2", userResponse)]
        def json = new JsonBuilder(carResponse)

        when:
        def result = mockMvc.perform(requestBuilder).andReturn()

        then:


        verifyAll(result) {
            response.status == HttpStatus.OK.value()
        }

    }
}


