package com.nttdata.entity;



import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Entidad Account de la tabla account
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Serializable {
    @NotNull
    private Long id_account_origin;
    @NotNull
    private Long id_account_destination;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private String creation_terminal;

}
