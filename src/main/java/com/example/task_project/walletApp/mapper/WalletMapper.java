package com.example.task_project.walletApp.mapper;

import com.example.task_project.walletApp.WalletEntity;
import com.example.task_project.walletApp.dto.WalletRs;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WalletMapper {
    @Mapping(target = "personId", source = "wallet.person.id")
    WalletRs toDto(WalletEntity wallet);

    List<WalletRs> toDtoList(List<WalletEntity> walletEntityList);

}
