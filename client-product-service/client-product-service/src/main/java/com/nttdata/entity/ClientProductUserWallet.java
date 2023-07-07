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
 * Entidad ClientProduct de la tabla client_product
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "client_product_user_wallet")
public class ClientProductUserWallet implements Serializable {
  @Transient
  public static final String SEQUENCE_NAME = "client_product_user_wallet_sequence";
  @Id
  private Long id;
  private Long idClientProduct;
  private Long idUser;
  private Integer status;
  private Date creationDate;
  private String creationTerminal;
  private Date modificationDate;
  private String modificationTerminal;
  @Transient
  private Message message;

}
