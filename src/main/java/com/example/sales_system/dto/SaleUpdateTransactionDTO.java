package com.example.salessystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleUpdateTransactionDTO {
    private Long transactionId;
    private Integer newQuantity;
    private Double newPrice;
}
