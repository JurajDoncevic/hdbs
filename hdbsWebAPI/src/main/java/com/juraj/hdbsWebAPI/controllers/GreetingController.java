package com.juraj.hdbsWebAPI.controllers;

import com.juraj.hdbsWebAPI.services.HDBSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Juraj on 25.4.2018..
 */
@RestController
public class GreetingController {

    @Autowired
    private HDBSService hdbsService;


    @GetMapping("/greeting")
    public String greeting() {
        return "HELLO";
    }
}
