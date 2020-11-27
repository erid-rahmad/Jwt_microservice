package com.tambuahciek.restaurant.controller;


import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/login")
public class ControlAut {

    @RequestMapping("/helo")
    public String loginpage(){
        return "helow";
    }
}
