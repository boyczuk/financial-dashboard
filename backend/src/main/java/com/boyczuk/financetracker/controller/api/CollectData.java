package com.boyczuk.financetracker.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectData {
    
    @GetMapping("/api/collect")
    public String getData() {
        return "Gathering csv file";
    }

    @PostMapping("/api/store")
    public String putData() {
        return "Putting data in PSQL DB";
    }
}
