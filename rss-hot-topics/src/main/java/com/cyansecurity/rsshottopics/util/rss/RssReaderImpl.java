/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.util.rss;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class RssReaderImpl implements RssReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(RssReaderImpl.class);

    @Override
    public List<RssItem> read(String url, int rssIndex) throws IOException, FeedException {
        LOGGER.info("Read URL: " + url);
        SyndFeed syndFeed = new SyndFeedInput().build(new XmlReader(new URL(url)));
        List<SyndEntry> items = syndFeed.getEntries();

        List<RssItem> rssItems = new ArrayList<>();
        for(SyndEntry item : items) {
            RssItem rssItem = new RssItem();
            rssItem.setRss(rssIndex);
            rssItem.setTitle(item.getTitle());
            rssItem.setLink(item.getLink());
            rssItems.add(rssItem);
        }
        return rssItems;
    }
}