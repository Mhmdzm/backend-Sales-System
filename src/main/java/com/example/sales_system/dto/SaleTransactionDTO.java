package com.example.salessystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleTransactionDTO {
    private Long productId;
    private Integer quantity;
    private Double price;
}
