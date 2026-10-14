package com.example.restapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/api/greetings")
    public GreetingResponse greet(@RequestParam(defaultValue = "world") String name) {
        return new GreetingResponse("Hello, " + name + "!");
    }

    @GetMapping("/api/greetings/{name}")
    public GreetingResponse greetByName(@PathVariable String name) {
        return new GreetingResponse("Hello, " + name + "!");
    }
}