package com.mayank.dinesplit.dto;

import java.math.BigDecimal;
import java.util.List;

public record BillItemResponse(
        Long itemId,
        String name,
        BigDecimal price,
        List<String> participants
) {
}
