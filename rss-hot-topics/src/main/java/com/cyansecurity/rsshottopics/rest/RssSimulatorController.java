/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.rest;

import com.cyansecurity.rsshottopics.util.rss.simulator.GoogleRssFeed;
import com.cyansecurity.rsshottopics.util.rss.simulator.NytimesRssFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.View;

@RestController
public class RssSimulatorController {

    @Autowired
    private GoogleRssFeed googleRssFeed;
    @Autowired
    private NytimesRssFeed nytimesRssFeed;

    @GetMapping(value="/google")
    public View google() {
        return googleRssFeed;
    }

    @GetMapping(value="/nytimes")
    public View nytimes() {
        return nytimesRssFeed;
    }
}
