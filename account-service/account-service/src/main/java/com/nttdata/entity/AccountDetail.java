package com.nttdata.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
/**
 * Entidad Person de la tabla account_detail
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account_detail")
public class AccountDetail implements Serializable {
    @Transient
    public static final String SEQUENCE_NAME = "account_detail_sequence";
    @Id
    private Long id;
    private Long id_account;
    private Long id_person;
    private Integer holder /*(titular)*/;
    private Integer authorized_signature;
    private Integer status;
    private Date creation_date;
    private String creation_terminal;
    private Date modification_date;
    private String modification_terminal;

}
