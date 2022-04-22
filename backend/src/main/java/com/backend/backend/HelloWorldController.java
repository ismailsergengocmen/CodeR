package com.backend.backend;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping
    public String helloWorld() {
        return "Hello world deneme 1, 2";
    }
}
