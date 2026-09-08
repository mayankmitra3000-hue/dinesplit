package com.mayank.dinesplit.dto;

import java.math.BigDecimal;
import java.util.List;

public record BillResponse(

        Long billId,

        String name,

        BigDecimal subtotal,

        BigDecimal serviceCharge,

        BigDecimal tax,

        BigDecimal total,

        List<BillItemResponse> items,

        List<PersonResponse> people

) {
}
