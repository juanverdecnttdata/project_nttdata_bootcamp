package com.nttdata.controller;

import com.nttdata.entity.AccountHistory;
import com.nttdata.model.Account;
import com.nttdata.model.ClientProduct;
import com.nttdata.service.AccountHistoryService;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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
    public ResponseEntity<List<AccountHistory>> listAccountsHistory(){
        List<AccountHistory> accountsHistory = accountHistoryService.getAll();
        return ResponseEntity.ok(accountsHistory);
    }
    /**
     * Metodo que guarda la informacion de la entidad AccountHistory
     * @param accountHistory Objeto de la entidad AccountHistory
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/save")
    public ResponseEntity<AccountHistory> saveAccountHistory(@RequestBody AccountHistory accountHistory){
        AccountHistory newAccountHistoryRepository = accountHistoryService.save(accountHistory);
        return ResponseEntity.ok(newAccountHistoryRepository);
    }
    /**
     * Metodo que obtiene los datos por cuenta
     * @param account lista de objetos de la entidad Account
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/listAccountHistoryByAccount")
    public ResponseEntity<List<AccountHistory>> listAccountHistoryByAccount(@RequestBody List<Account> account){
        List<AccountHistory> accountsHistory = accountHistoryService.getAll();
        AtomicReference<List<AccountHistory>> newAccountsHistory = new AtomicReference<>(accountHistoryService.getAll());

        Observable<AccountHistory> productsObservable = Observable.fromIterable(accountsHistory);
        productsObservable
                .filter(accountHistory -> account.stream().noneMatch(compare -> compare.getId_account().equals(accountHistory.getId_account())))
                .collect(Collectors.toList())
                .subscribe(
                        accountTmp -> newAccountsHistory.set(accountTmp),
                        error -> System.out.println("error " + error.getMessage())
                );
        //System.out.println(newAccountsHistory.get().size());
        return ResponseEntity.ok(newAccountsHistory.get());
    }
    /**
     * Metodo que obtiene los datos por cliente-producto
     * @param clientProducts lista de objetos de la entidad ClientProduct
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/listAccountHistoryByClientProduct")
    public ResponseEntity<List<AccountHistory>> listAccountHistoryByClientProduct(@RequestBody List<ClientProduct> clientProducts){
        List<AccountHistory> accountsHistory = accountHistoryService.getAll();
        AtomicReference<List<AccountHistory>> newAccountsHistory = new AtomicReference<>(new ArrayList<AccountHistory>());
        Observable<AccountHistory> productsObservable = Observable.fromIterable(accountsHistory);
        productsObservable
                .filter(accountHistory -> clientProducts.stream().noneMatch(compare -> compare.getId_client_product().equals(accountHistory.getId_client_product())))
                .collect(Collectors.toList())
                .subscribe(
                        clientProductTmp -> newAccountsHistory.set(clientProductTmp),
                        error -> System.out.println("error " + error.getMessage())
                );
        //System.out.println(newAccountsHistory.get().size());
        return ResponseEntity.ok(newAccountsHistory.get());
    }
}
