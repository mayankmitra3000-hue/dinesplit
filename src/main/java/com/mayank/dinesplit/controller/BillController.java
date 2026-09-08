package com.mayank.dinesplit.controller;

import com.mayank.dinesplit.dto.BillResponse;
import com.mayank.dinesplit.dto.CreateBillRequest;
import com.mayank.dinesplit.service.BillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    private final BillService billService;

    public BillController(BillService billService) {
        this.billService = billService;
    }

    @PostMapping
    public ResponseEntity<BillResponse> createBill(
            @Valid @RequestBody CreateBillRequest request) {

        BillResponse response =
                billService.createBill(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillResponse> getBill(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                billService.getBill(id)
        );
    }
}
