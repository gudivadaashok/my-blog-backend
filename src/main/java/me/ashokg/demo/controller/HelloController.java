package me.ashokg.demo.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HelloController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String custom() {
        return "Hello from Spring Boot";
    }
}