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
/**
 * Clase Controller de la entidad Person
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    /**
     * Metodo que busca todos los datos de la entidad Account
     * @return retorna una lista de la entidad Account
     */
    @GetMapping("/all")
    public ResponseEntity<List<Account>> listAccounts(){
        List<Account> accounts = accountService.getAll();
        return ResponseEntity.ok(accounts);
    }

    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Account
     * @param account Objeto de la entidad Account
     * @return retorna el objeto de la entidad account insertado o actualizado
     */
    @PostMapping("/save")
    public ResponseEntity<Account> saveAccount(@RequestBody Account account){
        Account newAccount = accountService.save(account);
        return ResponseEntity.ok(newAccount);
    }

    /**
     * Metodo que realiza la operacion de suma, resta dependiendo del tipo de operacion
     * @param account  objeto de la entidad Account
     * @return retorna un mensaje de confirmacion
     */
    @PostMapping("/operation")
    public ResponseEntity<Message> operation(@RequestBody Account account){
        Message message = accountService.doOperation(account);
        return ResponseEntity.ok(message);
    }

    /**
     * Metodo que busca las cuentas y muestra el saldo actual de ellas
     * @param account Objeto de la entidad Account
     * @return retorna una lista de la entidad QueryBalance
     */
    @PostMapping("/availableBalanceAccount")
    public ResponseEntity<List<QueryBalance>> availableBalanceAccount(@RequestBody Account account){
        List<QueryBalance> lstQueryBalance = accountService.availableBalanceAccount(account.getId_client());
        return ResponseEntity.ok(lstQueryBalance);
    }
    /**
     * Metodo que busca los datos client product y muestra el credito actual de ellas
     * @param clientProduct Objeto de la entidad ClientProduct
     * @return retorna una lista de la entidad QueryBalance
     */
    @PostMapping("/availableClientProduct")
    public ResponseEntity<List<QueryBalance>> availableClientProduct(@RequestBody ClientProduct clientProduct){
        List<QueryBalance> lstQueryBalance = accountService.listClientsProducts(clientProduct.getId_client());
        return ResponseEntity.ok(lstQueryBalance);
    }

    /**
     * Metodo que busca los movimientos de las cuentas por cliente
     * @param account Objeto de la entidad Account
     * @return retorna una lista historica de movimientos
     */
    @PostMapping("/listMovements")
    public ResponseEntity<List<AccountHistory>> listMovements(@RequestBody Account account){
        List<AccountHistory> lstAccountHistory = accountService.listMovements(account);
        return ResponseEntity.ok(lstAccountHistory);
    }
}
