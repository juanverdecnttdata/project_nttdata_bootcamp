package com.nttdata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Entidad AccountHistory de la tabla account_history
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory implements Serializable {
    @Id
    private Long id;
    private Long id_account;
    private Long id_client_product;
    private Integer operation_type; /*1 retiro, 2 deposito, 3 pago de credito*/
    private BigDecimal amount;
    private Integer status;
    private Date operation_date;
    private String operation_terminal;
    private Long id_account_destination;
    private Long id_account_origin;
}
