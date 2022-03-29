package com.lomalan.tweetcollector.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

  @GetMapping("/ping")
  public ResponseEntity<String> ping(@RequestParam String pingParam){
    return ResponseEntity.ok(pingParam);
  }
}
