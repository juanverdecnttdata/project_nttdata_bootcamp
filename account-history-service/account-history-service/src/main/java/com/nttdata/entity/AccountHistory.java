package com.nttdata.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class AccountHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_account_history;
    private Long id_account;
    private Long id_client_product;
    private Integer operation_type; /*1 retiro, 2 deposito, 3 pago de credito, 4 cargar consumo*/
    private BigDecimal amount;
    private Integer status;
    private Date operation_date;
    private String operation_terminal;

    public Long getId_account_history() {
        return id_account_history;
    }

    public void setId_account_history(Long id_account_history) {
        this.id_account_history = id_account_history;
    }

    public Long getId_account() {
        return id_account;
    }

    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }


    public Integer getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(Integer operation_type) {
        this.operation_type = operation_type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(Date operation_date) {
        this.operation_date = operation_date;
    }

    public String getOperation_terminal() {
        return operation_terminal;
    }

    public void setOperation_terminal(String operation_terminal) {
        this.operation_terminal = operation_terminal;
    }

    public Long getId_client_product() {
        return id_client_product;
    }

    public void setId_client_product(Long id_client_product) {
        this.id_client_product = id_client_product;
    }
}
