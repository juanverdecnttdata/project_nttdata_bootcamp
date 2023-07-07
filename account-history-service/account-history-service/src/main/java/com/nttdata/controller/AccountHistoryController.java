package com.nttdata.controller;

import com.nttdata.entity.AccountHistory;
import com.nttdata.model.Account;
import com.nttdata.model.ClientProduct;
import com.nttdata.service.AccountHistoryService;
import io.reactivex.rxjava3.core.Observable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ExecutionException;
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
        System.out.println("listAccountHistoryByAccount");
        List<AccountHistory> accountsHistory = null;
        try {
            accountsHistory = accountHistoryService.getAll().collectList().toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        AtomicReference<List<AccountHistory>> newAccountsHistory = new AtomicReference<>();
        Observable<AccountHistory> productsObservable = Observable.fromIterable(accountsHistory);
        productsObservable
                .filter(accountHistory -> account.stream().anyMatch(compare -> compare.getId().equals(accountHistory.getId_account())))
                .collect(Collectors.toList())
                .subscribe(
                        newAccountsHistory::set,
                        error -> System.out.println("error " + error.getMessage())
                );
        System.out.println(newAccountsHistory.get().size());

        return Flux.fromIterable(newAccountsHistory.get());
    }
    /**
     * Metodo que obtiene los datos por cliente-producto
     * @param clientProducts lista de objetos de la entidad ClientProduct
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/listAccountHistoryByClientProduct")
    public Flux<AccountHistory> listAccountHistoryByClientProduct(@RequestBody List<ClientProduct> clientProducts){
        List<AccountHistory> accountsHistory = null;
        try {
            accountsHistory = accountHistoryService.getAll().collectList().toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        AtomicReference<List<AccountHistory>> newAccountsHistory = new AtomicReference<>();
        Observable<AccountHistory> productsObservable = Observable.fromIterable(accountsHistory);
        productsObservable
                .filter(accountHistory -> clientProducts.stream().anyMatch(clientProduct -> clientProduct.getId().equals(accountHistory.getId_client_product())))
                .collect(Collectors.toList())
                .subscribe(
                        newAccountsHistory::set,
                        error -> System.out.println("error " + error.getMessage())
                );
        return Flux.fromIterable(newAccountsHistory.get());
    }
}
