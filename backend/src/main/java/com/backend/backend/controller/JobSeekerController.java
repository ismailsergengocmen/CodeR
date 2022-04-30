package com.backend.backend.controller;

import com.backend.backend.entity.JobSeeker;
import com.backend.backend.repository.JobSeekerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/jobseeker")
public class JobSeekerController {
    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @GetMapping("all")
    public List<JobSeeker> getAllJobSeekers() {
        return jobSeekerRepository.getAllJobSeekers();
    }

    @GetMapping("{user_id}")
    public JobSeeker findJobSeekersWithId(@PathVariable int user_id) {
        return jobSeekerRepository.findJobSeekersWithId(user_id);
    }

    @PostMapping("all")
    public void addNewJobSeeker(@RequestBody int user_id) {
        jobSeekerRepository.addNewJobSeeker(user_id);
    }
}
