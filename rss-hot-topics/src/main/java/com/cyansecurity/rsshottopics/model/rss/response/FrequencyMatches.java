/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.model.rss.response;

import com.cyansecurity.rsshottopics.entity.News;
import com.cyansecurity.rsshottopics.entity.Request;
import com.cyansecurity.rsshottopics.entity.Match;

import java.util.ArrayList;
import java.util.List;

public class FrequencyMatches implements FrequencyResponse {

    private List<MatchResponse> matches;

    public FrequencyMatches() {

    }

    public FrequencyMatches(Request request) {
        matches = new ArrayList<>();
        parse(request);
    }

    public List<MatchResponse> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchResponse> matches) {
        this.matches = matches;
    }

    private void parse(Request request) {
        for(Match match : request.getMatches()) {
            MatchResponse matchResponse = new MatchResponse();
            matchResponse.setKeyword(match.getWord());

            List<NewsResponse> newsList = new ArrayList<>();
            for(News news : match.getNews()) {
                NewsResponse responseNews = new NewsResponse();
                responseNews.setTitle(news.getTitle());
                responseNews.setLink(news.getLink());
                newsList.add(responseNews);
            }
            matchResponse.setNews(newsList);
            matches.add(matchResponse);
        }
    }
}
