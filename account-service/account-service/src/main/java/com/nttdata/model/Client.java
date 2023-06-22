package com.nttdata.model;



import com.nttdata.entity.Message;

import javax.persistence.*;
import java.util.Date;
/**
 * Entidad Client de la tabla client
 */
@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_client;
    private Long id_person;
    private Integer status;
    private Long id_client_type;
    private Date creation_date;
    private String creation_terminal;
    private Date modification_date;
    private String modification_terminal;
    @Transient
    private Message message;

    public Long getId_client() {
        return id_client;
    }

    public void setId_client(Long id_client) {
        this.id_client = id_client;
    }

    public Long getId_person() {
        return id_person;
    }

    public void setId_person(Long id_person) {
        this.id_person = id_person;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId_client_type() {
        return id_client_type;
    }

    public void setId_client_type(Long id_client_type) {
        this.id_client_type = id_client_type;
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
