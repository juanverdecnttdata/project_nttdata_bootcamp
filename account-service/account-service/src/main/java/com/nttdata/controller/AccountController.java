package com.nttdata.controller;

import com.nttdata.entity.Account;
import com.nttdata.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/all")
    public ResponseEntity<List<Account>> listAccounts(){
        List<Account> accounts = accountService.getAll();
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/save")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account){
        Account newAccount = accountService.save(account);
        return ResponseEntity.ok(newAccount);
    }
}
