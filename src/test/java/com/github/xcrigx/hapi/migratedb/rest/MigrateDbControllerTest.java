package com.github.xcrigx.hapi.migratedb.rest;

import com.github.xcrigx.hapi.migratedb.config.SpringConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {"spring.main.allow-bean-definition-overriding=true"}))
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = SpringConfig.class)
//@AutoConfigureMockMvc
@EnableAutoConfiguration
@ActiveProfiles("test")
public class MigrateDbControllerTest {

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MigrateDbControllerTest.class);

	@LocalServerPort
	int port;


	@Value("${server.servlet.context-path}")
	String contextPath;

	@Test
	public void testApi() throws URISyntaxException {

		String urlStr = String.format("http://localhost:%s%s/ping", port, contextPath);
		log.debug("urlStr->{}", urlStr);

		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders httpHeaders = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
		URI uri = new URI(urlStr);

		HttpStatusCode status = null;
		String responseBody = null;
		try {
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
			responseBody = response.getBody();
			status = response.getStatusCode();
		} catch (HttpStatusCodeException ex) {
			status = ex.getStatusCode();
		}

		log.debug("response->{} : {}", status, responseBody);
		assertEquals(HttpStatus.OK, status);
	}

}
