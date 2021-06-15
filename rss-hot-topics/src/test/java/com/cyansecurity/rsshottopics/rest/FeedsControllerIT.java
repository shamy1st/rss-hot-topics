package com.cyansecurity.rsshottopics.rest;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeedsControllerIT {

    @Value("${testing.google.rss.feed}")
    private String googleRss;
    @Value("${testing.nytimes.rss.feed}")
    private String nytimesRss;

    @Autowired
    private TestRestTemplate restTemplate;

    ResponseEntity<String> postAnalyse(List<String> urls) {
        HttpEntity<List<String>> request = new HttpEntity<>(urls, new HttpHeaders());
        return this.restTemplate.postForEntity("/analyse/new", request, String.class);
    }

    ResponseEntity<String> getFrequency(String uuid) {
        return this.restTemplate.getForEntity("/frequency/" + uuid, String.class);
    }

    @Test
    void noRssUrl() {
        ResponseEntity<String> response = postAnalyse(new ArrayList<>());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void oneRssUrl() {
        ResponseEntity<String> response = postAnalyse(Arrays.asList(googleRss));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void invalidRssUrl() {
        ResponseEntity<String> response = postAnalyse(Arrays.asList(googleRss, "https://www.google.com"));
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void requestNotFound() {
        ResponseEntity<String> response = getFrequency("8b5bd8ac-0860-4a4a-beec-124294d917e2");
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void test01() {
        ResponseEntity<String> uuid = postAnalyse(Arrays.asList(googleRss, nytimesRss));
        ResponseEntity<String> response = getFrequency(uuid.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        DocumentContext context = JsonPath.parse(response.getBody());
        List<String> keywords = context.read("$..keyword");
        assertEquals("biden", keywords.get(0));
        assertEquals("china", keywords.get(1));
        assertEquals("summit", keywords.get(2));
    }
}