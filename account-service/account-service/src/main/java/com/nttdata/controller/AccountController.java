package com.nttdata.controller;

import com.nttdata.entity.Account;
import com.nttdata.entity.Message;
import com.nttdata.entity.QueryBalance;
import com.nttdata.model.AccountHistory;
import com.nttdata.model.ClientProduct;
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
    @PostMapping("/operation")
    public ResponseEntity<Message> operation(@RequestBody Account account){
        Message message = accountService.doOperation(account);
        return ResponseEntity.ok(message);
    }

    @PostMapping("/availableBalanceAccount")
    public ResponseEntity<List<QueryBalance>> availableBalanceAccount(@RequestBody Account account){
        List<QueryBalance> lstQueryBalance = accountService.availableBalanceAccount(account.getId_client());
        return ResponseEntity.ok(lstQueryBalance);
    }

    @PostMapping("/availableClientProduct")
    public ResponseEntity<List<QueryBalance>> availableClientProduct(@RequestBody ClientProduct clientProduct){
        List<QueryBalance> lstQueryBalance = accountService.listClientsProducts(clientProduct.getId_client());
        return ResponseEntity.ok(lstQueryBalance);
    }

    @PostMapping("/listMovements")
    public ResponseEntity<List<AccountHistory>> listMovements(@RequestBody Account account){
        List<AccountHistory> lstAccountHistory = accountService.listMovements(account);
        return ResponseEntity.ok(lstAccountHistory);
    }
}
