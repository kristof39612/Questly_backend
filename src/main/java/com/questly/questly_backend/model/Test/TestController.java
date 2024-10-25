package com.questly.questly_backend.model.Test;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/test")
public class TestController {

    @PostMapping
    public String testPost(@RequestBody Test message){return message.message;}

    @GetMapping
    public String testGet(){return "Get works";}
}
