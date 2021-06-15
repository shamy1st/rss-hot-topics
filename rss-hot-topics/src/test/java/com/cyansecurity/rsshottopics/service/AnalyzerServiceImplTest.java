package com.cyansecurity.rsshottopics.service;

import com.cyansecurity.rsshottopics.util.rss.Keyword;
import com.cyansecurity.rsshottopics.util.rss.RssItem;
import com.rometools.rome.io.FeedException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnalyzerServiceImplTest {

    @Value("${testing.google.rss.feed}")
    private String googleRss;
    @Value("${testing.nytimes.rss.feed}")
    private String nytimesRss;

    @Value("${testing.google.news01.title}")
    private String titleGoogleNews01;
    @Value("${testing.google.news01.link}")
    private String linkGoogleNews01;

    @Autowired
    private AnalyzerServiceImpl analyzerService;

    @Test
    void analyse() throws IOException, FeedException {
        UUID uuid = analyzerService.analyse(Arrays.asList(googleRss, nytimesRss));
        assertTrue(uuid.toString().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }

    @Test
    void readAllRss() throws IOException, FeedException {
        List<RssItem> rssItems = analyzerService.readAllRss(Arrays.asList(googleRss, nytimesRss));
        assertEquals(12, rssItems.size());
        assertEquals(titleGoogleNews01, rssItems.get(0).getTitle());
        assertEquals(linkGoogleNews01, rssItems.get(0).getLink());
        assertEquals(0, rssItems.get(0).getRss());
    }

    @Test
    void parseAllRssItems() throws IOException, FeedException {
        List<RssItem> rssItems = analyzerService.readAllRss(Arrays.asList(googleRss, nytimesRss));
        Map<String, Keyword> keywords = analyzerService.parseAllRssItems(rssItems, 2);
        assertEquals(6, keywords.get("biden").getCount());
        assertEquals(5, keywords.get("china").getCount());
        assertEquals(3, keywords.get("summit").getCount());
    }

    @Test
    void sortKeywords() throws IOException, FeedException {
        List<RssItem> rssItems = analyzerService.readAllRss(Arrays.asList(googleRss, nytimesRss));
        Map<String, Keyword> keywords = analyzerService.parseAllRssItems(rssItems, 2);
        List<Keyword> sortedKeywords = analyzerService.sortKeywords(keywords);
        assertEquals("biden", sortedKeywords.get(0).getWord());
        assertEquals("china", sortedKeywords.get(1).getWord());
        assertEquals("summit", sortedKeywords.get(2).getWord());
    }

    @Test
    void saveTopMatchesToDB() throws IOException, FeedException {
        List<RssItem> rssItems = analyzerService.readAllRss(Arrays.asList(googleRss, nytimesRss));
        Map<String, Keyword> keywords = analyzerService.parseAllRssItems(rssItems, 2);
        List<Keyword> sortedKeywords = analyzerService.sortKeywords(keywords);
        UUID requestId = analyzerService.saveTopMatchesToDB(sortedKeywords);
        assertTrue(requestId.toString().matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$"));
    }
}