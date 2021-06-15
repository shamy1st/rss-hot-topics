/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.model.rss.response;

public class FrequencyMessage implements FrequencyResponse {

    private String message;

    public FrequencyMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
