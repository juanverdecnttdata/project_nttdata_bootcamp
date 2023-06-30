package com.nttdata.model;



import com.nttdata.entity.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.Date;
/**
 * Entidad AccountHistory de la tabla client_product
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientProduct {
    @Id
    private Long id;
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

}
