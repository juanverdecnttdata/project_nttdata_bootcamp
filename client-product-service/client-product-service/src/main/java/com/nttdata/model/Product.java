package com.nttdata.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entidad de la tabla product
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "product")
public class Product implements Serializable {

  @Id
  private Long id;
  private Integer id_product_type;
  private String name;
  private Integer status;
  private Date creation_date;
  private String creation_terminal;
  private Date modification_date;
  private String modification_terminal;
  private Integer comission_free_mainteance;
  private Integer n_movements;
  private Integer n_operation_month;
  private Integer free_movements;
  private BigDecimal amount_maintenance;
}