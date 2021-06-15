/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.service;

import com.cyansecurity.rsshottopics.entity.Request;
import com.cyansecurity.rsshottopics.model.rss.response.FrequencyMatches;
import com.cyansecurity.rsshottopics.repository.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FrequencyServiceImpl implements FrequencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrequencyServiceImpl.class);

    @Autowired
    private RequestRepository requestRepository;

    @Override
    public Optional<FrequencyMatches> getFrequencyResponse(UUID requestId) {
        Optional<Request> optional = requestRepository.findById(requestId);
        if(!optional.isPresent())
            return Optional.ofNullable(null);
        return Optional.ofNullable(new FrequencyMatches(optional.get()));
    }
}