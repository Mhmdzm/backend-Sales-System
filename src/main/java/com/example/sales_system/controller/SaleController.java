package com.example.salessystem.controller;

import com.example.salessystem.dto.SaleDTO;
import com.example.salessystem.dto.SaleUpdateTransactionDTO;
import com.example.salessystem.model.Sale;
import com.example.salessystem.model.SaleTransaction;
import com.example.salessystem.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    // Create a new sale with multiple transactions
    @PostMapping
    public ResponseEntity<Sale> create(@RequestBody SaleDTO dto) {
        return ResponseEntity.ok(saleService.createSale(dto));
    }

    // Fetch all sales
    @GetMapping
    public ResponseEntity<List<Sale>> getAll() {
        return ResponseEntity.ok(saleService.getAllSales());
    }

    // Update quantity and price of a sale transaction
    @PutMapping("/transactions")
    public ResponseEntity<SaleTransaction> updateTransaction(@RequestBody SaleUpdateTransactionDTO dto) {
        return ResponseEntity.ok(
            saleService.updateTransaction(
                dto.getTransactionId(),
                dto.getNewQuantity(),
                dto.getNewPrice()
            )
        );
    }
}
