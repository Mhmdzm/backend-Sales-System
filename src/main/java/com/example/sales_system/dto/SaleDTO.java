package com.example.salessystem.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDTO {
    private Long clientId;
    private String seller;
    private List<SaleTransactionDTO> transactions;
}
