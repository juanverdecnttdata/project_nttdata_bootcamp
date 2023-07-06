package com.nttdata.controller;

import com.nttdata.entity.Account;
import com.nttdata.entity.Message;
import com.nttdata.entity.QueryBalance;
import com.nttdata.entity.Transaction;
import com.nttdata.model.AccountHistory;
import com.nttdata.model.BalanceSummary;
import com.nttdata.model.Client;
import com.nttdata.model.ClientProduct;
import com.nttdata.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
    public Flux<Account> listAccounts(){
        return accountService.getAll();
    }

    /**
     * Metodo que realiza la insercion y actualizacion de la entidad Account
     * @param account Objeto de la entidad Account
     * @return retorna el objeto de la entidad account insertado o actualizado
     */
    @PostMapping("/save")
    public Mono<Account> saveAccount(@RequestBody Account account){
        return accountService.saveAccount(account);
    }

    /**
     * Metodo que realiza la operacion de suma, resta dependiendo del tipo de operacion
     * @param account  objeto de la entidad Account
     * @return retorna un mensaje de confirmacion
     */
    @PostMapping("/operation")
    public Mono<Message> operation(@RequestBody Account account){
        Mono<Message> message = accountService.doOperation(account);
        return message;
    }

    /**
     * Metodo que busca las cuentas y muestra el saldo actual de ellas
     * @param account Objeto de la entidad Account
     * @return retorna una lista de la entidad QueryBalance
     */
    @PostMapping("/availableBalanceAccount")
    public Flux<QueryBalance> availableBalanceAccount(@RequestBody Account account){
        return accountService.availableBalanceAccount(account.getId_client());
    }
    /**
     * Metodo que busca los datos client product y muestra el credito actual de ellas
     * @param clientProduct Objeto de la entidad ClientProduct
     * @return retorna una lista de la entidad QueryBalance
     */
    @PostMapping("/availableClientProduct")
    public Flux<QueryBalance> availableClientProduct(@RequestBody ClientProduct clientProduct){
        return accountService.listClientsProducts(clientProduct.getId_client());
    }

    /**
     * Metodo que busca los movimientos de las cuentas por cliente
     * @param account Objeto de la entidad Account
     * @return retorna una lista historica de movimientos
     */
    @PostMapping("/listMovements")
    public Flux<AccountHistory> listMovements(@RequestBody Account account){
        return accountService.listMovements(account);
    }

    @GetMapping("/test")
    public Mono<Client> listAccountById(){
        return accountService.accountwebclient();
    }

 /*   @GetMapping("/getclient")
    public Client getclient() throws ExecutionException, InterruptedException, TimeoutException {
        return accountService.getClientByMono();
    }*/

    @PostMapping("/transaction")
    public Mono<Message> transaction(@RequestBody @Valid Transaction transaction){
        return accountService.transaction(transaction);
    }

    @GetMapping("/getBalanceSumary/{id_client}")
    public Mono<BalanceSummary> getBalanceSumary(@PathVariable("id_client") Long id_client){
        return accountService.getBalanceSumary(id_client);
    }
}
