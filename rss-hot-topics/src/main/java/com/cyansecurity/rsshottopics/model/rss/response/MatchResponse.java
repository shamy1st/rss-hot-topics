/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.model.rss.response;

import java.util.ArrayList;
import java.util.List;

public class MatchResponse {

    private String keyword;
    private List<NewsResponse> news;

    public MatchResponse() {
        news = new ArrayList<>();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<NewsResponse> getNews() {
        return news;
    }

    public void setNews(List<NewsResponse> news) {
        this.news = news;
    }
}