/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.rest;

import com.cyansecurity.rsshottopics.model.rss.response.FrequencyResponse;
import com.cyansecurity.rsshottopics.model.rss.response.FrequencyMatches;
import com.cyansecurity.rsshottopics.model.rss.response.FrequencyMessage;
import com.cyansecurity.rsshottopics.service.AnalyzerService;
import com.cyansecurity.rsshottopics.service.FrequencyService;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class FeedsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeedsController.class);

    @Value("${minimum.urls}")
    private int minimumUrls;
    @Value("${logging.minimum.urls}")
    private String minimumUrlsMsg;
    @Value("${logging.error.invalid.rss.url}")
    private String invalidRssUrlErrorMsg;
    @Value("${logging.error.reading.rss}")
    private String readingRssErrorMsg;
    @Value("${logging.error.parsing.rss}")
    private String parsingRssErrorMsg;
    @Value("${logging.error.server}")
    private String serverErrorMsg;
    @Value("${logging.error.request.not.found}")
    private String requestNotFoundErrorMsg;

    @Autowired
    private AnalyzerService analyzerService;
    @Autowired
    private FrequencyService frequencyService;

    @PostMapping(value="/analyse/new")
    public ResponseEntity<String> analyse(@RequestBody List<String> urls) {

        LOGGER.info("Validate RSS URLs ...");
        if(urls.size() < minimumUrls) {
            LOGGER.error(minimumUrlsMsg);
            return new ResponseEntity<>(minimumUrlsMsg, HttpStatus.BAD_REQUEST);
        } else if(!isValidRssUrls(urls)) {
            LOGGER.error(invalidRssUrlErrorMsg + getInvalidRssUrl(urls));
            return new ResponseEntity<>(invalidRssUrlErrorMsg + getInvalidRssUrl(urls), HttpStatus.BAD_REQUEST);
        }

        LOGGER.info("Analyse RSS URLs ...");
        try {
            UUID id = analyzerService.analyse(urls);
            return new ResponseEntity<>(id.toString(), HttpStatus.OK);
        } catch (IOException e) {
            LOGGER.error(readingRssErrorMsg, e);
            return new ResponseEntity<>(readingRssErrorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (FeedException e) {
            LOGGER.error(parsingRssErrorMsg, e);
            return new ResponseEntity<>(parsingRssErrorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            LOGGER.error(serverErrorMsg, e);
            return new ResponseEntity<>(serverErrorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/frequency/{id}")
    public ResponseEntity<FrequencyResponse> frequency(@PathVariable("id") UUID requestId) {
        LOGGER.info("Get frequency by requestId: " + requestId);
        try {
            Optional<FrequencyMatches> optional = frequencyService.getFrequencyResponse(requestId);
            if(!optional.isPresent()) {
                LOGGER.error(requestNotFoundErrorMsg + requestId);
                return new ResponseEntity<>(new FrequencyMessage(requestNotFoundErrorMsg + requestId), HttpStatus.NOT_FOUND);
            }
            LOGGER.info("Frequency response done!");
            return new ResponseEntity<>(optional.get(), HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.error(serverErrorMsg, e);
            return new ResponseEntity<>(new FrequencyMessage(serverErrorMsg), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private boolean isValidRssUrls(List<String> urls) {
        for(String url : urls) {
            if(!isValidUrl(url))
                return false;
        }
        return true;
    }

    private String getInvalidRssUrl(List<String> urls) {
        for(String url : urls) {
            if(!isValidUrl(url))
                return url;
        }
        return "";
    }

    private boolean isValidUrl(String address) {
        try {
            URL url = new URL(address);
            SyndFeedInput input = new SyndFeedInput();
            input.build(new XmlReader(url));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}