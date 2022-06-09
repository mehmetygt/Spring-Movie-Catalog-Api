 package com.example.bookstoreapi;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseIntegrationTest {

	protected String BEARER_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZW5lbWUiLCJleHAiOjI2NDM5MDg5MDF9.sYGOrhQj3_Q6_1wJ7qismrgCX4zyP2dysoADm5fspNml1B9dISeoOOYrxpVJUkOLlyA6zw2iakiKPRlXcS9l8A";

	@LocalServerPort
	protected int serverPort;

	@Autowired
	protected TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
	}

}
