package com.nttdata.controller;

import com.nttdata.entity.AccountHistory;
import com.nttdata.model.Account;
import com.nttdata.model.ClientProduct;
import com.nttdata.service.AccountHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
/**
 * Clase Controller de la entidad AccountHistory
 */
@RestController
@RequestMapping("/accountHistory")
public class AccountHistoryController {
    @Autowired
    private AccountHistoryService accountHistoryService;
    /**
     * Metodo que obtiene la informacion de la entidades AccountHistory
     * @return retorna un objeto de la entidad AccountHistory
     */
    @GetMapping("/all")
    public Flux<AccountHistory> listAccountsHistory(){
        return accountHistoryService.getAll();
    }
    /**
     * Metodo que guarda la informacion de la entidad AccountHistory
     * @param accountHistory Objeto de la entidad AccountHistory
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/save")
    public Mono<AccountHistory> saveAccountHistory(@RequestBody AccountHistory accountHistory){
        return accountHistoryService.save(accountHistory);
    }
    /**
     * Metodo que obtiene los datos por cuenta
     * @param account lista de objetos de la entidad Account
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/listAccountHistoryByAccount")
    public Flux<AccountHistory> listAccountHistoryByAccount(@RequestBody List<Account> account){
        Flux<AccountHistory> accountsHistory = accountHistoryService.getAll();
        List<AccountHistory> newAccountsHistory = new ArrayList<AccountHistory>();

        accountsHistory
                .filter(accountHistory -> account.stream().noneMatch(compare -> compare.getId().equals(accountHistory.getId_account())))
                .collect(Collectors.toList())
                .subscribe(
                        accountTmp -> newAccountsHistory.addAll(accountTmp),
                        error -> System.out.println("error " + error.getMessage())
                );
        //System.out.println(newAccountsHistory.get().size());
        return Flux.fromIterable(newAccountsHistory);
    }
    /**
     * Metodo que obtiene los datos por cliente-producto
     * @param clientProducts lista de objetos de la entidad ClientProduct
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/listAccountHistoryByClientProduct")
    public Flux<AccountHistory> listAccountHistoryByClientProduct(@RequestBody List<ClientProduct> clientProducts){
        Flux<AccountHistory> accountsHistory = accountHistoryService.getAll();
        List<AccountHistory> newAccountsHistory = new ArrayList<AccountHistory>();
        accountsHistory
                .filter(accountHistory -> clientProducts.stream().noneMatch(compare -> compare.getId().equals(accountHistory.getId_client_product())))
                .collect(Collectors.toList())
                .subscribe(
                        clientProductTmp -> newAccountsHistory.addAll(clientProductTmp),
                        error -> System.out.println("error " + error.getMessage())
                );
        System.out.println(newAccountsHistory.size());
        return Flux.fromIterable(newAccountsHistory);
    }
}
