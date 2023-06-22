package com.nttdata.interfaces;

import java.math.BigDecimal;

@FunctionalInterface
public interface MathOperation {
    BigDecimal operation(BigDecimal currentAmount,BigDecimal amount);
}
