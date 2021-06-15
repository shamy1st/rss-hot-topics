/***
 ** Copyright 2021, Ahmed Elshamy, shamy1st@icloud.com, All rights reserved.
 **/
package com.cyansecurity.rsshottopics.repository;

import com.cyansecurity.rsshottopics.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Integer> {
    Optional<Match> findByWord(String word);
}
