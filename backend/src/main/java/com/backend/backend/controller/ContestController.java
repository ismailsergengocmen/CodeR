package com.backend.backend.controller;

import com.backend.backend.entity.Contest;
import com.backend.backend.repository.ContestRepository;
import com.backend.backend.util.ContestParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/contest")
public class ContestController {

    @Autowired
    ContestRepository contestRepository;

    @GetMapping("all")
    public List<Contest> getAllContests() {
        return contestRepository.getAllContests();
    }

    @GetMapping("{contest_id}")
    public Contest getContestWithId(@PathVariable int contest_id) {
        return contestRepository.getContestWithId(contest_id);
    }

    @PostMapping("create")
    public Integer createContest(@RequestBody Contest contest) {
        contest.setCreate_date(LocalDateTime.now().withNano(0));
        return contestRepository.createContest(contest);
    }

    @PostMapping("enter")
    public Boolean addParticipant(@RequestBody ContestParticipant contestParticipant) {
        return contestRepository.addParticipant(contestParticipant.getUser_id(), contestParticipant.getContest_id());
    }

    @PutMapping("update")
    public Boolean updateContest(@RequestBody Contest contest) {
        return contestRepository.updateContest(contest);
    }

}
