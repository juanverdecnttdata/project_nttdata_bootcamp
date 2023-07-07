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
 * Entidad ClientProduct de la tabla client_product.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "client_product")
public class ClientProduct implements Serializable {
  @Transient
  public static final String SEQUENCE_NAME = "client_product_sequence";
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
