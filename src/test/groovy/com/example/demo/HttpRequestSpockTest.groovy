package com.example.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class HttpRequestSpockTest extends Specification{
    @LocalServerPort
    int port

    @Autowired
    TestRestTemplate restTemplate

    def "spring context loads for rest template" () {
        expect:
        restTemplate.getForObject("http://localhost:" + port + "/actuator/health", String) == "{\"status\":\"UP\"}"
    }

}
