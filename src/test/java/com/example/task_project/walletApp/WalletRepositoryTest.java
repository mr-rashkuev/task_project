package com.example.task_project.walletApp;

import com.example.task_project.PersonApp.PersonEntity;
import com.example.task_project.PersonApp.PersonRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class WalletRepositoryTest {

    @Autowired
    private WalletRepository underTest;
    @Autowired
    private PersonRepository personRepository;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckWalletBalanceExist() {
        PersonEntity personEntity = new PersonEntity("Andrew");
        personRepository.save(personEntity);
        WalletEntity wallet = new WalletEntity(personEntity, new BigDecimal(500));
        underTest.save(wallet);
        Optional<BigDecimal> expected = underTest.getWalletBalance(wallet.getId());
        assertThat(expected).isPresent();
    }

    @Test
    void itShouldCheckWalletBalanceNotExist() {
        WalletEntity wallet = new WalletEntity(new PersonEntity("Andrew"), new BigDecimal(500));
        Optional<BigDecimal> expected = underTest.getWalletBalance(wallet.getId());
        assertThat(expected).isEmpty();
    }


}