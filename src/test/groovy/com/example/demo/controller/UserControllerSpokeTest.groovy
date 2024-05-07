package com.example.demo.controller

import com.example.demo.controller.request.UserRequest
import com.example.demo.controller.response.IDResponse
import com.example.demo.controller.response.UserResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerSpokeTest extends Specification {

    @LocalServerPort
    def port

    @Autowired
    TestRestTemplate restTemplate

    def baseUrl

    void setup() {
        baseUrl = "http://localhost:" + port
    }

    def "when 'getUsers' is performed then the response has a 200 status"() {

        given:
        def url = baseUrl + "/users"

        when:
        ResponseEntity<List<UserResponse>> response = restTemplate.getForEntity(url, List<UserResponse>)

        then:
        verifyAll(response) {
            statusCode == HttpStatus.OK
            body.size() >= 1
        }

    }

    def "when 'getUserById' is performed then the response has a 200 status"() {

        given:
        def url = baseUrl + "/user/100"

        when:
        ResponseEntity<UserResponse> response = restTemplate.getForEntity(url, UserResponse)

        then:
        verifyAll(response) {
            statusCode == HttpStatus.OK
            body.id == 100
        }

    }

    def "when 'postUser' is performed then the response has a 201 status"() {

        given:
        def url = baseUrl + "/users"

        and:
        def request = new HttpEntity<>(new UserRequest(name, age))

        when:
        ResponseEntity<IDResponse> postResponse = restTemplate.exchange(url, HttpMethod.POST, request, IDResponse)

        then:
        verifyAll(postResponse) {
            statusCode == HttpStatus.CREATED
            body.id >= 0
        }

        when:
        ResponseEntity<UserResponse> getResponse = restTemplate.getForEntity(url + "/" + postResponse.body.id, UserResponse)

        then:
        verifyAll(getResponse) {
            statusCode == HttpStatus.OK
            body.id == postResponse.body.id
            body.name == name
            body.age == age
        }

        where:
        name    | age
        "user1" | 20
        "user2" | 21
        "user3" | 22

    }

    def "when 'postUser' is performed with a bad request then the response has a 400 status" () {
        given:
        def url = baseUrl + "/users"

        and:
        def request = new HttpEntity<>(new UserRequest("Test", age))

        when:
        ResponseEntity<IDResponse> response = restTemplate.exchange(url, HttpMethod.POST, request, IDResponse)

        then:
        response.statusCode == HttpStatus.BAD_REQUEST

        where:
        age << (0..17)

    }

}
