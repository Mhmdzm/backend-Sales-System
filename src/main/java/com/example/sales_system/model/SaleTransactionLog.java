package com.example.salessystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleTransactionLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long transactionId;

    private Integer oldQuantity;
    private Integer newQuantity;

    private Double oldPrice;
    private Double newPrice;

    private String updatedBy;

    private LocalDateTime updatedAt;
}
