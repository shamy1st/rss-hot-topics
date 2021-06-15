/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.util.rss;

import java.util.*;

public class Keyword implements Comparable<Keyword> {

    private String word;
    private int count;
    private boolean rssUrls[];
    private Set<RssItem> items;

    public Keyword(String word, int rssUrlsSize) {
        this.word = word;
        this.rssUrls = new boolean[rssUrlsSize];
        this.count = 1;
        this.items = new LinkedHashSet<>();
    }

    public String getWord() {
        return word;
    }

    public int getCount() {
        return count;
    }

    public void increment() {
        this.count++;
    }

    public void foundInRss(int rssIndex) {
        this.rssUrls[rssIndex] = true;
    }

    public void addItem(RssItem item) {
        if(!items.contains(item))
            items.add(item);
    }

    public Set<RssItem> getItems() {
        return items;
    }

    public boolean isFoundInAllRss() {
        for(boolean rss : rssUrls) {
            if(!rss)
                return false;
        }
        return true;
    }

    @Override
    public int compareTo(Keyword other) {
        if(this.count > other.count) {
            return 1;
        } else if(this.count < other.count) {
            return -1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "word='" + word + '\'' +
                ", count=" + count +
                ", rssUrls=" + Arrays.toString(rssUrls) +
                ", items=" + items +
                '}';
    }
}