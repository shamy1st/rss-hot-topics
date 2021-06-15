/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.util.rss;

import com.rometools.rome.io.FeedException;

import java.io.IOException;
import java.util.List;

public interface RssReader {
    List<RssItem> read(String url, int rssIndex) throws IOException, FeedException;
}