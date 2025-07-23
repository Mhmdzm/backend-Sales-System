package com.example.salessystem.service;

import com.example.salessystem.dto.SaleDTO;
import com.example.salessystem.dto.SaleTransactionDTO;
import com.example.salessystem.model.*;
import com.example.salessystem.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final SaleTransactionRepository transactionRepository;
    private final SaleTransactionLogRepository transactionLogRepository;

    // Create a new sale with multiple transactions
    public Sale createSale(SaleDTO saleDTO) {
        Client client = clientRepository.findById(saleDTO.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        List<SaleTransaction> transactionList = new ArrayList<>();
        double total = 0;

        Sale sale = Sale.builder()
                .client(client)
                .seller(saleDTO.getSeller())
                .creationDate(LocalDateTime.now())
                .total(0.0)
                .transactions(transactionList)
                .build();

        for (SaleTransactionDTO txDTO : saleDTO.getTransactions()) {
            Product product = productRepository.findById(txDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            SaleTransaction transaction = SaleTransaction.builder()
                    .sale(sale)
                    .product(product)
                    .quantity(txDTO.getQuantity())
                    .price(txDTO.getPrice())
                    .build();

            transactionList.add(transaction);
            total += txDTO.getQuantity() * txDTO.getPrice();
        }

        sale.setTotal(total);
        return saleRepository.save(sale); // cascade will save transactions
    }

    // Fetch all sales
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // Update transaction quantity and price with logging
    public SaleTransaction updateTransaction(Long transactionId, Integer newQty, Double newPrice) {
        SaleTransaction transaction = transactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));

        // Log the old and new values before updating
        SaleTransactionLog log = SaleTransactionLog.builder()
                .transactionId(transaction.getId())
                .oldQuantity(transaction.getQuantity())
                .newQuantity(newQty)
                .oldPrice(transaction.getPrice())
                .newPrice(newPrice)
                .updatedAt(LocalDateTime.now())
                .build();

        // Apply updates
        transaction.setQuantity(newQty);
        transaction.setPrice(newPrice);

        // Recalculate total sale amount
        Sale sale = transaction.getSale();
        double newTotal = sale.getTransactions().stream()
                .mapToDouble(tx -> {
                    if (tx.getId().equals(transactionId)) {
                        return newQty * newPrice;
                    } else {
                        return tx.getQuantity() * tx.getPrice();
                    }
                })
                .sum();
        sale.setTotal(newTotal);

        // Save all
        transactionLogRepository.save(log);
        transactionRepository.save(transaction);
        saleRepository.save(sale);

        return transaction;
    }
}
