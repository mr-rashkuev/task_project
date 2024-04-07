package com.example.task_project.walletApp;

import com.example.task_project.walletApp.dto.WalletTransactionRq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
@Validated
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{walletId}/balance")
    public BigDecimal getWalletBalance(@Valid @PathVariable String walletId){
        return walletService.getWalletBalance(walletId);
    }
    @PostMapping("/")
    public void walletTransaction(@Valid @RequestBody WalletTransactionRq walletRq){
        walletService.makeWalletTransaction(walletRq);
    }

}
