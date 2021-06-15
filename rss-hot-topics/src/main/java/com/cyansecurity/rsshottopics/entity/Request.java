/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.entity;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Request {

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy="request")
    private List<Match> matches;

    public Request() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }
}