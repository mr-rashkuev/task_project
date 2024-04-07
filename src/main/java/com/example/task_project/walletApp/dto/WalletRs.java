package com.example.task_project.walletApp.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class WalletRs {

    private String id;
    private Long personId;
    private BigDecimal balance;
}
