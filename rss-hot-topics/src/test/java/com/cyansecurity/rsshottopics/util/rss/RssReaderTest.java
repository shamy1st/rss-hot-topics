package com.cyansecurity.rsshottopics.util.rss;

import com.rometools.rome.io.FeedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RssReaderTest {

    @Value("${testing.google.rss.feed}")
    private String googleRss;

    @Value("${testing.google.news01.title}")
    private String titleNews01;
    @Value("${testing.google.news01.link}")
    private String linkNews01;
    @Value("${testing.google.news02.title}")
    private String titleNews02;
    @Value("${testing.google.news02.link}")
    private String linkNews02;
    @Value("${testing.google.news03.title}")
    private String titleNews03;
    @Value("${testing.google.news03.link}")
    private String linkNews03;
    @Value("${testing.google.news04.title}")
    private String titleNews04;
    @Value("${testing.google.news04.link}")
    private String linkNews04;
    @Value("${testing.google.news05.title}")
    private String titleNews05;
    @Value("${testing.google.news05.link}")
    private String linkNews05;

    @Autowired
    private RssReader rssReader;
    
    @Test
    void read() throws IOException, FeedException {
        List<RssItem> list = rssReader.read(googleRss, 0);
        assertEquals(5, list.size());
        assertEquals(titleNews01, list.get(0).getTitle());
        assertEquals(linkNews01, list.get(0).getLink());
        assertEquals(0, list.get(0).getRss());
        assertEquals(titleNews02, list.get(1).getTitle());
        assertEquals(linkNews02, list.get(1).getLink());
        assertEquals(0, list.get(1).getRss());
        assertEquals(titleNews03, list.get(2).getTitle());
        assertEquals(linkNews03, list.get(2).getLink());
        assertEquals(0, list.get(2).getRss());
        assertEquals(titleNews04, list.get(3).getTitle());
        assertEquals(linkNews04, list.get(3).getLink());
        assertEquals(0, list.get(3).getRss());
        assertEquals(titleNews05, list.get(4).getTitle());
        assertEquals(linkNews05, list.get(4).getLink());
        assertEquals(0, list.get(4).getRss());
    }
}