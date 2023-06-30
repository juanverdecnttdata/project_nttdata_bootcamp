package com.nttdata.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
/**
 * Entidad QueryBalance, usado para mostrar el saldo actual de las cuentas o productos de credito
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QueryBalance {
    private Long id_account;
    private Long id_client_product;
    private BigDecimal balance;

}
