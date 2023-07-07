package com.nttdata.service;

import com.nttdata.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.ExecutionException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @InjectMocks
  private UserService userService;

  @Test
  @DisplayName("testing the save method for check his correct performance")
  public void testSave() throws ExecutionException, InterruptedException {
    User userExpected = new User(1L, "47552460", "1", "944206474", "112022 20 233388 1", "juanverdec@gmail.com", null);
    User userRegistered;
    userRegistered = userService.getUserById(1L).toFuture().get();
    Assertions.assertEquals(userExpected.getId(), userRegistered.getId());
  }
}
