/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Lob
    @Column
    private String link;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="match_news",
            joinColumns=@JoinColumn(name="news_id"),
            inverseJoinColumns=@JoinColumn(name="match_id"))
    private List<Match> matches;

    public News() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void addMatch(Match match) {
        if(matches == null)
            matches = new ArrayList<>();
        this.matches.add(match);
    }
}