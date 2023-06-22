package com.nttdata.entity;

import java.math.BigDecimal;

public class QueryBalance {

    private Long id_account;
    private Long id_client_product;
    private BigDecimal balance;

    public QueryBalance(Long id_account, Long id_client_product, BigDecimal balance) {
        this.id_account = id_account;
        this.id_client_product = id_client_product;
        this.balance = balance;
    }

    public Long getId_account() {
        return id_account;
    }

    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }

    public Long getId_client_product() {
        return id_client_product;
    }

    public void setId_client_product(Long id_client_product) {
        this.id_client_product = id_client_product;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
