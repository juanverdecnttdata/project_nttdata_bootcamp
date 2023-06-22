package com.nttdata.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
/**
 * Entidad de la tabla product
 */
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_product;
    private Integer id_product_type; /* (Pasivo o Activo) */
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

    public Long getId_product() {
        return id_product;
    }

    public void setId_product(Long id_product) {
        this.id_product = id_product;
    }

    public Integer getId_product_type() {
        return id_product_type;
    }

    public void setId_product_type(Integer id_product_type) {
        this.id_product_type = id_product_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getComission_free_mainteance() {
        return comission_free_mainteance;
    }

    public void setComission_free_mainteance(Integer comission_free_mainteance) {
        this.comission_free_mainteance = comission_free_mainteance;
    }

    public Integer getN_movements() {
        return n_movements;
    }

    public void setN_movements(Integer n_movements) {
        this.n_movements = n_movements;
    }

    public Integer getN_operation_month() {
        return n_operation_month;
    }

    public void setN_operation_month(Integer n_operation_month) {
        this.n_operation_month = n_operation_month;
    }

    public Integer getFree_movements() {
        return free_movements;
    }

    public void setFree_movements(Integer free_movements) {
        this.free_movements = free_movements;
    }

    public BigDecimal getAmount_maintenance() {
        return amount_maintenance;
    }

    public void setAmount_maintenance(BigDecimal amount_maintenance) {
        this.amount_maintenance = amount_maintenance;
    }
}
