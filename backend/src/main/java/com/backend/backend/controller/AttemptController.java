package com.backend.backend.controller;

import com.backend.backend.entity.CodingAttempt;
import com.backend.backend.entity.CodingAttemptWithTestCases;
import com.backend.backend.entity.NonCodingAttempt;
import com.backend.backend.repository.AttemptRepository;
import com.backend.backend.entity.SubmitResult;
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
    public List<CodingAttemptWithTestCases> getPastChallengeAttempt(@PathVariable Integer question_id, @RequestParam(required = false) Integer point, @RequestParam(required = false) Integer user_id) {
        return attemptRepository.getPastChallengeAttempt(question_id, point, user_id);
    }

    @GetMapping("past/noncoding/{question_id}")
    public List<NonCodingAttempt> getPastNonCodingQuestionAttempts(@PathVariable Integer question_id, @RequestParam(required = false) Integer user_id) {
        return attemptRepository.getPastNonCodingQuestionAttempts(question_id, user_id);
    }
}
