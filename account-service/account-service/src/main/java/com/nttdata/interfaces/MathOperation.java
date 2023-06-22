package com.nttdata.interfaces;

import java.math.BigDecimal;

/**
 * Interfaz funcional que realiza operaciones matematicas
 */
@FunctionalInterface
public interface MathOperation {
    BigDecimal operation(BigDecimal currentAmount,BigDecimal amount);
}
