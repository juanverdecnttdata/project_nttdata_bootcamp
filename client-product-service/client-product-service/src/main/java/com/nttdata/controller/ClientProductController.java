package com.nttdata.controller;

import com.nttdata.entity.ClientProduct;
import com.nttdata.entity.ClientProductDebtAccount;
import com.nttdata.entity.ClientProductLog;
import com.nttdata.entity.ClientProductUserWallet;
import com.nttdata.model.Account;
import com.nttdata.model.SummaryClientProduct;
import com.nttdata.service.ClientProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Clase Controller de la entidad ClientProduct
 */
@RestController
@RequestMapping("/clientProduct")
public class ClientProductController {
  @Autowired
  private ClientProductService clientProductService;

  /**
   * Metodo que busca todos los datos de la entidad ClientProduct
   *
   * @return retorna una lista de la entidad ClientProduct
   */
  @GetMapping("/all")
  public Flux<ClientProduct> listClientsProducts() {
    return clientProductService.getAll();
  }

  /**
   * Metodo que realiza la insercion y actualizacion de la entidad ClientProduct
   *
   * @param clientProduct Objeto de la entidad ClientProduct
   * @return retorna el objeto de la entidad ClientProduct insertado o actualizado
   */
  @PostMapping("/save")
  public Mono<ClientProduct> saveClient(@RequestBody ClientProduct clientProduct) {
    return clientProductService.saveClientProduct(clientProduct);
  }

  /**
   * Meotodo que busca un cliente producto
   *
   * @param id Identificador de la entidad ClientProduct
   * @return retorna un objeto de la entidad ClientProduct
   */
  @GetMapping("/getClientProductById/{id}")
  public Mono<ClientProduct> getClientProductById(@PathVariable("id") Long id) {
    return clientProductService.getClientProductById(id);
  }

  @GetMapping("/getAllClientProductLog")
  public Flux<ClientProductLog> getAllClientProductLog() {
    return clientProductService.getAllClientProductLog();
  }

  @PostMapping("/productSummary")
  public Mono<SummaryClientProduct> productSummary(@RequestBody ClientProduct clientProduct) {
    return clientProductService.clientProductByClientId(clientProduct.getId_client());
  }

  @PostMapping("/assignAccountToDebitCard")
  public Mono<ClientProductDebtAccount> assignAccountToDebitCard(@RequestBody ClientProductDebtAccount clientProductDebtAccount) {
    return clientProductService.assignAccountToDebitCard(clientProductDebtAccount);
  }

  @PostMapping("/getMainAccount")
  public Mono<Account> getMainAccount(@RequestBody ClientProduct clientProduct) {
    return clientProductService.getMainAccount(clientProduct);
  }

  @PostMapping("/assignWallerUserToDebitCard")
  public Mono<ClientProductUserWallet> assignAccountToDebitCard(@RequestBody ClientProductUserWallet clientProductUserWallet) {
    return clientProductService.assignUserToDebitCard(clientProductUserWallet);
  }

}
