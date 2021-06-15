/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.service;

import com.cyansecurity.rsshottopics.entity.Match;
import com.cyansecurity.rsshottopics.entity.News;
import com.cyansecurity.rsshottopics.entity.Request;
import com.cyansecurity.rsshottopics.repository.NewsRepository;
import com.cyansecurity.rsshottopics.repository.RequestRepository;
import com.cyansecurity.rsshottopics.repository.MatchRepository;
import com.cyansecurity.rsshottopics.util.rss.Keyword;
import com.cyansecurity.rsshottopics.util.rss.RssItem;
import com.cyansecurity.rsshottopics.util.rss.RssReader;
import com.rometools.rome.io.FeedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class AnalyzerServiceImpl implements AnalyzerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnalyzerServiceImpl.class);

    @Value("${most.matches}")
    private int mostMatches;
    @Value("${english.stop.words}")
    private String stopWordsStr;
    @Value("${ignore.regular.expression}")
    private String ignoreRegularExpr;
    @Value("${trim.starting.with}")
    private String trimStartSubstrings;
    @Value("${trim.ending.with}")
    private String trimEndSubstrings;

    @Autowired
    private RssReader rssReader;

    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private NewsRepository newsRepository;

    @Override
    public UUID analyse(List<String> urls) throws IOException, FeedException {
        LOGGER.info("Read all RSS items ...");
        List<RssItem> rssItems = readAllRss(urls);
        LOGGER.info("Parse all RSS items ...");
        Map<String, Keyword> keywords = parseAllRssItems(rssItems, urls.size());
        LOGGER.info("Sort keywords ...");
        List<Keyword> sortedKeywords = sortKeywords(keywords);
        LOGGER.info("Save top matches to DB ...");
        UUID requestId = saveTopMatchesToDB(sortedKeywords);
        LOGGER.info("Save done!");
        return requestId;
    }

    protected List<RssItem> readAllRss(List<String> urls) throws IOException, FeedException {
        List<RssItem> rssItems = new ArrayList<>();
        int rssIndex = 0;
        for(String url : urls) {
            rssItems.addAll(rssReader.read(url, rssIndex++));
        }
        return rssItems;
    }

    protected Map<String, Keyword> parseAllRssItems(List<RssItem> rssItems, int rssUrlsSize) {
        Set<String> ignoredWordsMap = prepareIgnoreWords();
        Map<String, Keyword> keywords = new HashMap<>();
        for(RssItem item : rssItems) {
            String[] words = item.getTitle().split(" ");
            for(String word : words) {
                word = trimBothSides(word);
                word = word.toLowerCase();
                if(!ignoredWordsMap.contains(word) && !word.matches(ignoreRegularExpr)) {
                    updateIfExistAddIfNot(keywords, word, rssUrlsSize, item);
                }
            }
        }
        return keywords;
    }

    protected String trimBothSides(String word) {
        String[] trimStartSub = trimStartSubstrings.split(" ");
        for(String startSub : trimStartSub) {
            if(word.startsWith(startSub)) {
                word = word.replaceAll("^"+startSub, "");
                break;
            }
        }
        String[] trimEndSub = trimEndSubstrings.split(" ");
        for(String endSub : trimEndSub) {
            if(word.endsWith(endSub)) {
                word = word.replaceAll(endSub+"$", "");
                break;
            }
        }
        return word;
    }

    protected Set<String> prepareIgnoreWords() {
        String[] ignoredWords = stopWordsStr.split(" ");
        Set<String> ignoredWordsMap = new HashSet<>();
        for(String word : ignoredWords) {
            ignoredWordsMap.add(word.toLowerCase());
        }
        return ignoredWordsMap;
    }

    protected void updateIfExistAddIfNot(Map<String, Keyword> keywords, String word,
                                       int rssUrlsSize, RssItem item) {
        if(!keywords.containsKey(word)) {
            keywords.put(word, new Keyword(word, rssUrlsSize));
        } else {
            keywords.get(word).increment();
        }
        keywords.get(word).foundInRss(item.getRss());
        keywords.get(word).addItem(item);
    }

    protected List<Keyword> sortKeywords(Map<String, Keyword> keywords) {
        List<Keyword> list = new ArrayList<>();
        list.addAll(keywords.values());
        Collections.sort(list);
        Collections.reverse(list);
        return list;
    }

    protected UUID saveTopMatchesToDB(List<Keyword> list) {
        int mostMatchesCount = mostMatches;
        Request request = insertRequestToDB();

        for (Keyword keyword : list) {
            if (mostMatchesCount > 0 && keyword.isFoundInAllRss()) {
                Match match = insertMatchToDB(keyword.getWord(), request);
                for (RssItem item : keyword.getItems()) {
                    News news = insertNewsToDB(item.getTitle(), item.getLink(), match);
                }
                mostMatchesCount--;
            }
        }
        return request.getId();
    }

    private Request insertRequestToDB() {
        Request request = new Request();
        requestRepository.save(request);
        return request;
    }

    private Match insertMatchToDB(String word, Request request) {
        Match match = new Match();
        match.setWord(word);
        match.setRequest(request);
        matchRepository.save(match);
        return match;
    }

    private News insertNewsToDB(String title, String link, Match match) {
        News news;
        Optional<News> optional = newsRepository.findByLink(link);
        if(optional.isPresent()) {
            news = optional.get();
        } else {
            news = new News();
            news.setTitle(title);
            news.setLink(link);
        }

        news.addMatch(match);
        newsRepository.save(news);
        return news;
    }
}