package com.backend.backend.controller;

import com.backend.backend.entity.Company;
import com.backend.backend.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/company")
public class CompanyController {
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("all")
    public List<Company> getAllCompanies() {
        return companyRepository.getAllCompanies();
    }

    @GetMapping("{user_id}")
    public Company findCompanyWithId(@PathVariable int user_id) {
        return companyRepository.findCompanyWithId(user_id);
    }

    @PostMapping("all")
    public void addNewCompany(@RequestBody int user_id) {
        companyRepository.addNewCompany(user_id);
    }
}
