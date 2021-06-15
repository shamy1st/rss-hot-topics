/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.util.rss;

public class RssItem {

    private int rssIndex;
    private String title;
    private String link;

    public int getRss() {
        return rssIndex;
    }

    public void setRss(int rssIndex) {
        this.rssIndex = rssIndex;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}