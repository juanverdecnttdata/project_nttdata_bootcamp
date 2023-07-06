package com.nttdata.model;

import com.nttdata.entity.AccountLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Entidad AccountHistory de la tabla account_history
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceSummary implements Serializable {
     List<AccountLog> listAccountLog;
     List<ClientProductLog> lstClientProductLog;
}
