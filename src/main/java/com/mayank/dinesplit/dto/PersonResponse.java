package com.mayank.dinesplit.dto;

import java.math.BigDecimal;

public record PersonResponse(
        Long personId,
        String name,
        BigDecimal amount
) {
}
