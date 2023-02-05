package com.ken.authorapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ken.shared.models.HelloWorld;

@RestController
public class GreetingController {

  @GetMapping("/hello-world")
  public ResponseEntity<String> getHealth() {
    HelloWorld helloWorld = new HelloWorld();
    helloWorld.setGreeting("こんにちは");
    return ResponseEntity.ok(helloWorld.getGreeting());
  }
  
}
