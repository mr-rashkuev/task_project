package com.example.task_project.walletApp;


import com.example.task_project.exception.BusinessException;
import com.example.task_project.walletApp.dto.WalletTransactionRq;
import com.example.task_project.walletApp.operation.Operation;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service

public class WalletService {

    private final WalletRepository walletRepository;
    private final Map<OperationType, Operation> operationMap;

    public WalletService(WalletRepository walletRepository, List<Operation> operationList) {
        this.walletRepository = walletRepository;
        this.operationMap = operationList.stream().collect(Collectors.toMap(Operation::getOperationType, Function.identity()));
    }

    public BigDecimal getWalletBalance(String walletId) {
        return walletRepository.getWalletBalance(walletId)
                .orElseThrow(() -> new BusinessException("Не найден кошелёк с id " + walletId));
    }

    public void makeWalletTransaction(WalletTransactionRq walletTransactionRq) {
        OperationType operationType = transformOperation(walletTransactionRq.getOperation());
        WalletEntity wallet = walletRepository.findById(walletTransactionRq.getWalletId())
                .orElseThrow(() -> new BusinessException("Не найден кошелёк с id " + walletTransactionRq.getWalletId()));
        Operation operation = operationMap.get(operationType);
        if(operation == null){
            throw new BusinessException("Неизвестный тип операции " + operationType);
        }
        BigDecimal balance = operation.apply(wallet.getBalance(), walletTransactionRq.getAmount());
        wallet.setBalance(balance);
        walletRepository.save(wallet);

    }

    private OperationType transformOperation(String operationType){
        try{
            return OperationType.valueOf(operationType);
        }catch (IllegalArgumentException e){throw new BusinessException("Operation может принимать значения DEPOSIT или WITHDRAW");

        }
    }

}
