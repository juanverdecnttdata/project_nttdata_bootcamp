package com.nttdata.model;



import com.nttdata.entity.Message;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
/**
 * Entidad Account de la tabla account
 */
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_account;
    private Long id_client;
    private Integer status;
    private Date creation_date;
    private String creation_terminal;
    private Date modification_date;
    private String modification_terminal;
    private BigDecimal balance/* (saldo)*/;
    private Long id_product;
    @Transient
    private Message message;
    @Transient
    private Long id_client_product;
    @Transient
    private Integer operation_type;
    @Transient
    private BigDecimal amount;

    public Account() {
    }

    public Long getId_account() {
        return id_account;
    }

    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public String getCreation_terminal() {
        return creation_terminal;
    }

    public void setCreation_terminal(String creation_terminal) {
        this.creation_terminal = creation_terminal;
    }

    public Date getModification_date() {
        return modification_date;
    }

    public void setModification_date(Date modification_date) {
        this.modification_date = modification_date;
    }

    public String getModification_terminal() {
        return modification_terminal;
    }

    public void setModification_terminal(String modification_terminal) {
        this.modification_terminal = modification_terminal;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
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

    public Long getId_client_product() {
        return id_client_product;
    }

    public void setId_client_product(Long id_client_product) {
        this.id_client_product = id_client_product;
    }
}
