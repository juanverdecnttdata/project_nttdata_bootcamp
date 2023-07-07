package com.nttdata.service;

import com.nttdata.entity.Message;
import com.nttdata.entity.User;
import com.nttdata.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private SequenceGeneratorService sequenceGeneratorService;
  public Mono<User> save(@RequestBody User user) {

    User newUser = null;
    try {
      Message message = new Message("001", "User created");
      user.setMessage(message);
      user.setId(sequenceGeneratorService.getSequenceNumber(User.SEQUENCE_NAME));
      newUser = userRepository.save(user).toFuture().get();
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    } catch (ExecutionException e) {
      throw new RuntimeException(e);
    }
    return Mono.just(newUser);
  }

  public Flux<User> getAll() {
    return userRepository.findAll();
  }
}
