package com.backend.backend.controller;

import com.backend.backend.entity.CodingAttempt;
import com.backend.backend.entity.NonCodingAttempt;
import com.backend.backend.repository.AttemptRepository;
import com.backend.backend.util.SubmitResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/attempt")
public class AttemptController {
    @Autowired
    AttemptRepository attemptRepository;

    @PostMapping("submit/challenge")
    public SubmitResult submitChallenge(@RequestBody CodingAttempt codingAttempt) {
        return attemptRepository.submitChallenge(codingAttempt);
    }

    @PostMapping("submit/noncoding")
    public Boolean submitNonCodingQuestion(@RequestBody NonCodingAttempt nonCodingAttempt) {
        return attemptRepository.submitNonCodingQuestion(nonCodingAttempt);
    }

    @GetMapping("past/challenge/{question_id}")
    public List<CodingAttempt> getPastChallengeAttempt(@PathVariable Integer question_id, @RequestParam(required = false) Integer point) {
        return attemptRepository.getPastChallengeAttempt(question_id, point);
    }

    @GetMapping("past/noncoding/{question_id}")
    public List<NonCodingAttempt> getPastNonCodingQuestionAttempts(@PathVariable Integer question_id) {
        return attemptRepository.getPastNonCodingQuestionAttempts(question_id);
    }
}
