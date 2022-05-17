package com.backend.backend.controller;

import com.backend.backend.entity.Attempt;
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
            return questionRepository.createChallenge(challenge);
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
            return questionRepository.createNonCodingQuestion(nonCodingQuestion);
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
