/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.service;

import com.rometools.rome.io.FeedException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface AnalyzerService {
    UUID analyse(List<String> urls) throws IOException, FeedException;
}