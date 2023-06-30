package com.brunosong.example1.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:9999/")
@RestController
public class ApiController {


    @RequestMapping("/api/cors/test")
    public String test(){

        return "안녕";

    }

}
