package com.backend.backend.controller;

import com.backend.backend.entity.*;
import com.backend.backend.repository.ForumRepository;
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

    @Autowired
    ForumRepository forumRepository;

    @GetMapping("all")
    public List<Question> getAllQuestions(@RequestParam(required = false) List<String> category, @RequestParam(required = false) List<Integer> difficulty) {
        return questionRepository.getAllQuestions(category, difficulty);
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

    @PostMapping("challenge/create")
    public Integer createChallenge(@RequestBody Challenge challenge) {
        challenge.setCreate_date(LocalDateTime.now().withNano(0));
        try {
            int challenge_id = questionRepository.createChallenge(challenge);
            forumRepository.createForum(new Forum(challenge_id, LocalDateTime.now().withNano(0), challenge.getQuestion_title() + "'s Forum", null));
            return challenge_id;
        } catch (SQLException e) {
            return null;
        }
    }

    @PutMapping("challenge/update")
    public Boolean updateChallenge(@RequestBody Challenge challenge) {
        return questionRepository.updateChallenge(challenge);
    }

    @GetMapping("noncoding/all")
    public List<NonCodingQuestion> getAllNonCodingQuestions() {
        return questionRepository.getAllNonCodingChallenges();
    }

    @GetMapping("noncoding/{question_id}")
    public NonCodingQuestion getNonCodingQuestionWithId(@PathVariable int question_id) {
        return questionRepository.getNonCodingQuestionWithId(question_id);
    }

    @PostMapping("noncoding/create")
    public Integer createNonCodingQuestion(@RequestBody NonCodingQuestion nonCodingQuestion) {
        nonCodingQuestion.setCreate_date(LocalDateTime.now().withNano(0));

        try {
            int noncoding_id = questionRepository.createNonCodingQuestion(nonCodingQuestion);
            forumRepository.createForum(new Forum(noncoding_id, LocalDateTime.now().withNano(0), nonCodingQuestion.getQuestion_title() + "'s Forum", null));
            return noncoding_id;
        } catch (SQLException e) {
            return null;
        }
    }

    @PutMapping("noncoding/update")
    public Boolean updateNonCodingQuestion(@RequestBody NonCodingQuestion nonCodingQuestion) {
        return questionRepository.updateNonCodingQuestion(nonCodingQuestion);
    }

    @PostMapping("like")
    public Boolean likeQuestion(@RequestBody Attempt questionUserData) {
        return questionRepository.likeQuestion(questionUserData.getUser_id(), questionUserData.getQuestion_id(), questionUserData.getPoint());
    }

    @PostMapping("getlike")
    public Integer getQuestionPoint(@RequestBody Attempt questionUserData) {
        return questionRepository.getQuestionPoint(questionUserData.getUser_id(), questionUserData.getQuestion_id());
    }
}
