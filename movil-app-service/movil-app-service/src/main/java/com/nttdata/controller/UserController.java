package com.nttdata.controller;

import com.nttdata.entity.User;
import com.nttdata.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RestController
@RequestMapping("/user")
public class UserController {
  @Autowired
  private UserService userService;

  @PostMapping("/save")
  public Mono<User> save(@RequestBody User user) {
    return userService.save(user);
  }

  @GetMapping("/all")
  public Flux<User> listUser() {
    return userService.getAll();
  }
  @GetMapping("/getUserById/{id}")
  public Mono<User> getUserById(@PathVariable("id") Long id){
    return userService.getUserById(id);
  }

}
