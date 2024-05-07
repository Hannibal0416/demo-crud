package com.example.demo

import com.example.demo.controller.UserController
import com.example.demo.controller.response.UserResponse
import com.example.demo.domain.Car
import com.example.demo.domain.User
import com.example.demo.repository.CarRepository
import com.example.demo.repository.UserRepository
import com.example.demo.service.CarService
import com.example.demo.service.UserService
import spock.lang.Specification
import spock.lang.Subject

import java.awt.*

class ExampleSpecification extends Specification {

    void setupSpec() {
        // setup code that needs to be run once at the start
    }

    void setup() {
        // setup code that needs to be run before every test method
    }

    void cleanup() {
        // code that tears down things at the end of a test method
    }

    void cleanupSpec() {
        // code that tears down everything at the end when al tests have run
    }

    def "Should be a simple assertion"() {
        expect:
        1 == 1
    }

    def "should demonstrate given-when-then"() {
        when:
        def polygon = new Polygon(3)

        then:
        thrown(RuntimeException)

    }


    def "should expect an Exception to be thrown for a number of invalid inputs #sides"() {
        when:
        def polygon = new Polygon(sides)

        then:
        thrown(RuntimeException)

        where:
        sides << [0, 1, 2]

    }

    def "should use data tables for calculating max"() {
        expect:
        Math.max(a,b) == max

        where:
        a | b | max
        1 | 3 | 3
        7 | 4 | 7
        0 | 0 | 0
    }


    def "should be able to mock a concrete class" () {
        given:
        UserService userService = Mock()
//        def userService = Mock(UserService)
        @Subject
        def userController = new UserController(userService)

        when:
        userController.getUserById(1L)
        userController.deleteUserById(1L)

        then:
        1 * userService.getById(1L)
        1 * userService.delete(1L)
//        2 * userService.delete(1L)

    }

    def "should be able to create a stub and a helper method" () {
        given:
        UserRepository userRepository = Stub()
        userRepository.findById(1L) >> Optional<User>.of(new User(1L, "name", 10, []))
        @Subject
        def userService = new UserService(userRepository)

        when:
        def userResponse = userService.getById(1L)

        then:
        checkUserResponse(userResponse)
    }

    private void checkUserResponse(UserResponse userResponse) {
        assert userResponse != null
        assert userResponse.id == 1L
    }

    def "should demonstrate 'with'" () {
        given:
        CarRepository carRepository = Stub()
        carRepository.findById(1L) >> Optional<Car>.of(new Car(1L, "model 1", new User()))
        @Subject
        def carService = new CarService(carRepository)

        when:
        def carResponse = carService.getById(1L)

        then:
        with (carResponse) {
            id == 1L
            model == "model 1"
        }
    }

    def "should demonstrate 'verifyAll'" () {
        given:
        CarRepository carRepository = Stub()
        carRepository.findById(1L) >> Optional<Car>.of(new Car(1L, "model 1", new User()))
        @Subject
        def carService = new CarService(carRepository)

        when:
        def carResponse = carService.getById(1L)

        then:
        verifyAll (carResponse) {
            id == 1L
            model == "model 1"
        }
    }
//
    def "should demonstrate specifications as documentation" () {
        given: "a car repository with a model 1"
        CarRepository carRepository = Stub()
        carRepository.findById(1L) >> Optional<Car>.of(new Car(1L, "model 1", new User()))

        and: "a car service initialized with the car repository"
        def carService = new CarService(carRepository)

        when: "use the car service to find a car with id"
        def carResponse = carService.getById(1L)

        then: "the car should be found"
        verifyAll (carResponse) {
            id == 1L
            model == "model 1"
        }
    }
}
