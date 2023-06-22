package com.nttdata.feignclient;

import com.nttdata.entity.Account;
import com.nttdata.model.AccountHistory;
import com.nttdata.model.Client;
import com.nttdata.model.ClientProduct;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="account-history-service",path = "accountHistory", url = "http://localhost:8088")
public interface AccountHistoryFeignClient {
    @PostMapping("/save")
    public AccountHistory save(@RequestBody AccountHistory accountHistory);

    @PostMapping("/listAccountHistoryByAccount")
    public List<AccountHistory> listAccountHistoryByAccount(@RequestBody List<Account> lstAccount);

    @PostMapping("/listAccountHistoryByClientProduct")
    public List<AccountHistory> listAccountHistoryByClientProduct(@RequestBody List<ClientProduct> clientProducts);
}
