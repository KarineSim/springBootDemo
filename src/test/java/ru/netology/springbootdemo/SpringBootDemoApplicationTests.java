package ru.netology.springbootdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private GenericContainer<?> myAppDev = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    private GenericContainer<?> myAppProd = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeEach
    public void setUp() {
        myAppDev.start();
        myAppProd.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> entityFromDev = restTemplate.getForEntity("http://localhost:" + myAppDev.getMappedPort(8080) + "/profile", String.class);
        ResponseEntity<String> entityFromProd = restTemplate.getForEntity("http://localhost:" + myAppProd.getMappedPort(8081) + "/profile", String.class);

        assertEquals(entityFromDev.getBody(), "Current profile is dev");
        assertEquals(entityFromProd.getBody(), "Current profile is production");
    }

}
