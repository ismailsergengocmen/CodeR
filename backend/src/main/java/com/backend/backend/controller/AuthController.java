package com.backend.backend.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.backend.backend.entity.User;
import com.backend.backend.repository.CompanyRepository;
import com.backend.backend.repository.EditorRepository;
import com.backend.backend.repository.JobSeekerRepository;
import com.backend.backend.repository.UserRepository;
import com.backend.backend.util.TypedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private EditorRepository editorRepository;
    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @PostMapping("register")
    public void registerNewUser(@RequestBody TypedUser typedUser) {
        String hashedPassword = BCrypt.withDefaults().hashToString(12, typedUser.getPassword().toCharArray());
        typedUser.setPassword(hashedPassword);

        int user_id = userRepository.addNewUser(typedUser.getUser());

        switch (typedUser.getType()) {
            case "jobseeker":
                jobSeekerRepository.addNewJobSeeker(user_id);
                break;
            case "company":
                companyRepository.addNewCompany(user_id);
                break;
            case "editor":
                editorRepository.addNewEditor(user_id);
                break;
        }
    }

    @GetMapping("login")
    public boolean login(@RequestBody User user) {
        return userRepository.isUserExists(user) != null;
    }
}
