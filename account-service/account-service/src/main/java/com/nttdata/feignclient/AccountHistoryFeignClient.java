package com.nttdata.feignclient;

import com.nttdata.entity.Account;
import com.nttdata.entity.Constant;
import com.nttdata.model.AccountHistory;
import com.nttdata.model.ClientProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


/**
 * Clase FeingClient que se conecta al microservicio account-history-service
 */
@FeignClient(name="account-history-service",path = "accountHistory", url = Constant.urlAccountHistory)
public interface AccountHistoryFeignClient {

    /**
     * Metodo que se conecta al servicio remoto y guarda la informacion de la entidad AccountHistory
     * @param accountHistory Objeto de la entidad AccountHistory
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/save")
    public Mono<AccountHistory> save(@RequestBody AccountHistory accountHistory);
    /**
     * Metodo que se conecta al servicio remoto y obtiene los datos por cuenta
     * @param lstAccount lista de objetos de la entidad Account
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/listAccountHistoryByAccount")
    public Flux<AccountHistory> listAccountHistoryByAccount(@RequestBody List<Account> lstAccount);
    /**
     * Metodo que se conecta al servicio remoto y obtiene los datos por cliente-producto
     * @param clientProducts lista de objetos de la entidad ClientProduct
     * @return retorna el objeto insertado o actualizado
     */
    @PostMapping("/listAccountHistoryByClientProduct")
    public Flux<AccountHistory> listAccountHistoryByClientProduct(@RequestBody List<ClientProduct> clientProducts);
}
