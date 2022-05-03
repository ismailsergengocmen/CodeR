package com.backend.backend.controller;

import com.backend.backend.entity.Challenge;
import com.backend.backend.entity.NonCodingQuestion;
import com.backend.backend.entity.Question;
import com.backend.backend.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/question")
public class QuestionController {
    @Autowired
    QuestionRepository questionRepository;

    @GetMapping("all")
    public List<Question> getAllQuestions(@RequestParam(required = false) List<String> category) {
        return questionRepository.getAllQuestions(category);
    }

    @GetMapping("{question_id}")
    public Question getQuestionWithId(@PathVariable int question_id) {
        return  questionRepository.getQuestionWithId(question_id);
    }

    @GetMapping("challenge/all")
    public List<Challenge> getAllChallenges() {
        return questionRepository.getAllChallenges();
    }

    @GetMapping("challenge/{question_id}")
    public Challenge getChallengeWithId(@PathVariable int question_id) {
        return questionRepository.getChallengeWithId(question_id);
    }

    @PostMapping("challenge")
    public Integer createChallenge(@RequestBody Challenge challenge) {
        challenge.setCreate_date(LocalDateTime.now().withNano(0));

        try {
            return questionRepository.createChallenge(challenge);
        } catch (SQLException e) {
            return null;
        }
    }

    @GetMapping("noncoding/all")
    public List<NonCodingQuestion> getAllNonCodingQuestions() {
        return questionRepository.getAllNonCodingChallenges();
    }

    @GetMapping("noncoding/{question_id}")
    public NonCodingQuestion getNonCodingQuestionWithId(@PathVariable int question_id) {
        return questionRepository.getNonCodingQuestionWithId(question_id);
    }

    @PostMapping("noncoding")
    public Integer createNonCodingQuestion(@RequestBody NonCodingQuestion nonCodingQuestion) {
        nonCodingQuestion.setCreate_date(LocalDateTime.now().withNano(0));

        try {
            return questionRepository.createNonCodingQuestion(nonCodingQuestion);
        } catch (SQLException e) {
            return null;
        }
    }
}
