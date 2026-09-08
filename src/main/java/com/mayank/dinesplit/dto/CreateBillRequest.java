package com.mayank.dinesplit.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;

public record CreateBillRequest(

        @NotBlank(message = "Bill name is required")
        String name,

        @NotEmpty(message = "At least one person is required")
        List<@NotBlank String> people,

        @NotEmpty(message = "At least one item is required")
        List<@Valid ItemRequest> items,

        @DecimalMin(value = "0.00", message = "Service charge cannot be negative")
        BigDecimal serviceCharge,

        @DecimalMin(value = "0.00", message = "Tax cannot be negative")
        BigDecimal tax

) {
}
