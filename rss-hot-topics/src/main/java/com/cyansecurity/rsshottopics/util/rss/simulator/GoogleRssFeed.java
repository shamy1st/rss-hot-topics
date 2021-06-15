/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.util.rss.simulator;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class GoogleRssFeed extends AbstractRssFeedView {

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

    @Override
    protected void buildFeedMetadata(Map<String, Object> model,
                                     Channel feed, HttpServletRequest request) {
        feed.setTitle("Google RSS Feed Sample");
        feed.setDescription("shamy1st@icloud.com");
        feed.setLink("http://www.shamy1st.com");
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model,
                                        HttpServletRequest request,
                                        HttpServletResponse response) {
        List<Item> items = new ArrayList<>();
        items.add(getItem(titleNews01, linkNews01));
        items.add(getItem(titleNews02, linkNews02));
        items.add(getItem(titleNews03, linkNews03));
        items.add(getItem(titleNews04, linkNews04));
        items.add(getItem(titleNews05, linkNews05));
        return items;
    }

    private Item getItem(String title, String link) {
        Item item = new Item();
        item.setTitle(title);
        item.setLink(link);
        return item;
    }
}