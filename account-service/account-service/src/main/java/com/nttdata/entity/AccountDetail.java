package com.nttdata.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
/**
 * Entidad Person de la tabla account_detail
 */
@Entity
public class AccountDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_account_detail;
    private Long id_account;
    private Long id_person;
    private Integer holder /*(titular)*/;
    private Integer authorized_signature;
    private Integer status;
    private Date creation_date;
    private String creation_terminal;
    private Date modification_date;
    private String modification_terminal;

    public Long getId_account_detail() {
        return id_account_detail;
    }

    public void setId_account_detail(Long id_account_detail) {
        this.id_account_detail = id_account_detail;
    }

    public Long getId_account() {
        return id_account;
    }

    public void setId_account(Long id_account) {
        this.id_account = id_account;
    }

    public Long getId_person() {
        return id_person;
    }

    public void setId_person(Long id_person) {
        this.id_person = id_person;
    }

    public Integer getHolder() {
        return holder;
    }

    public void setHolder(Integer holder) {
        this.holder = holder;
    }

    public Integer getAuthorized_signature() {
        return authorized_signature;
    }

    public void setAuthorized_signature(Integer authorized_signature) {
        this.authorized_signature = authorized_signature;
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
}
