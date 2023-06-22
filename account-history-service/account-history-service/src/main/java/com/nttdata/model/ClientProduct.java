package com.nttdata.model;



import com.nttdata.entity.Message;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Entidad ClientProduct de la tabla client_product
 */
@Entity
public class ClientProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_client_product;
    private Long id_client;
    private Long id_product;
    private Long id_account;
    private BigDecimal credit_limit;
    private BigDecimal credit;
    private Integer status;
    private Date creation_date;
    private String creation_terminal;
    private Date modification_date;
    private String modification_terminal;
    @Transient
    private Message message;

    public Long getId_client_product() {
        return id_client_product;
    }

    public void setId_client_product(Long id_client_product) {
        this.id_client_product = id_client_product;
    }

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public Long getId_account() {
        return id_account;
    }

    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }

    public BigDecimal getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(BigDecimal credit_limit) {
        this.credit_limit = credit_limit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
