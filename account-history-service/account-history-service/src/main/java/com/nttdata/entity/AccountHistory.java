package com.nttdata.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Entidad de la tabla account_history.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account_history")
public class AccountHistory implements Serializable {
  @Transient
  public static final String SEQUENCE_NAME = "account_history_sequence";
  @Id
  private Long id;
  private Long id_account;
  private Long id_client_product;
  private Integer operation_type; /*1 retiro, 2 deposito, 3 pago de credito, 4 cargar consumo*/
  private BigDecimal amount;
  private Integer status;
  private Date operation_date;
  private String operation_terminal;
  private Long id_account_destination;
  private Long id_account_origin;
}
