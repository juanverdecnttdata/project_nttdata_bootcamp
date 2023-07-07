package com.nttdata.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entidad ClientProduct de la tabla client_product
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "client_product_debt_account")
public class ClientProductDebtAccount implements Serializable {
    @Transient
    public static final String SEQUENCE_NAME = "client_product_debt_account_sequence";
    @Id
    private Long id;
    private Long id_client_product;
    private Long id_account;
    private Integer status;
    private Date creation_date;
    private String creation_terminal;
    private Date modification_date;
    private String modification_terminal;
    private Integer main_account;
    @Transient
    private Message message;

}
