package com.backend.backend.controller;

import com.backend.backend.entity.Interview;
import com.backend.backend.repository.InterviewRepository;
import com.backend.backend.util.InterviewQuestionResult;
import com.backend.backend.util.InterviewParticipant;
import com.backend.backend.util.InterviewResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/interview")
public class InterviewController {

    @Autowired
    InterviewRepository interviewRepository;

    @GetMapping("all")
    public List<Interview> getAllInterviews(@RequestParam(required = false) Integer company_id, @RequestParam(required = false) Integer user_id, @RequestParam(required = false) String interview_name) {
        return interviewRepository.getAllInterviews(company_id, user_id, interview_name);
    }

    @GetMapping("{interview_id}")
    public Interview getInterviewWithId(@PathVariable Integer interview_id) {
        return interviewRepository.getInterviewWithId(interview_id);
    }

    @PostMapping("create")
    public Integer createInterview(@RequestBody Interview interview) {
        interview.setCreate_date(LocalDateTime.now().withNano(0));
        return interviewRepository.createInterview(interview);
    }

    @PostMapping("enter")
    public Boolean addJobSeeker(@RequestBody InterviewParticipant interviewParticipant) {
        return interviewRepository.addJobSeeker(interviewParticipant.getUser_id(), interviewParticipant.getInterview_id());
    }

    @PutMapping("update")
    public Boolean updateContest(@RequestBody Interview interview) {
        return interviewRepository.updateInterview(interview);
    }

    @GetMapping("results/{interview_id}")
    public List<InterviewResult> getInterviewResults(@PathVariable Integer interview_id) {
        return interviewRepository.getInterviewResults(interview_id);
    }
}