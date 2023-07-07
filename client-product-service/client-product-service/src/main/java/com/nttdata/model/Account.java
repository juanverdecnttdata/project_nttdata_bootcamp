package com.nttdata.model;

import com.nttdata.entity.Message;
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
 * Entidad Account de la tabla account.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account")
public class Account implements Serializable {

  @Transient
  public static final String SEQUENCE_NAME = "account_sequence";
  @Id
  private Long id;
  private Long id_client;
  private Integer status;
  private Date creation_date;
  private String creation_terminal;
  private Date modification_date;
  private String modification_terminal;
  private BigDecimal balance/* (saldo)*/;
  private Long id_product;
  private Integer n_transactions;
  @Transient
  private Message message;

  @Transient
  private Long id_client_product;
  @Transient
  private Integer operation_type;
  @Transient
  private BigDecimal amount;

}
