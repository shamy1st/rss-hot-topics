/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.service;

import com.cyansecurity.rsshottopics.model.rss.response.FrequencyMatches;

import java.util.Optional;
import java.util.UUID;

public interface FrequencyService {
    Optional<FrequencyMatches> getFrequencyResponse(UUID requestId) throws Exception;
}