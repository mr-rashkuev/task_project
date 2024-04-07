package com.example.task_project.walletApp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.example.task_project.PersonApp.PersonEntity;
import com.example.task_project.PersonApp.PersonRepository;
import com.example.task_project.PersonApp.PersonService;
import com.example.task_project.exception.BusinessException;
import com.example.task_project.walletApp.dto.WalletTransactionRq;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private PersonRepository personRepository;
    private ObjectMapper objectMapper;
    private WalletEntity wallet;

    @BeforeEach
    void init() {
        PersonEntity person1 = new PersonEntity("Andrew");
        personRepository.save(person1);
        wallet = walletRepository.save(new WalletEntity(person1, BigDecimal.valueOf(500.0)));
        objectMapper = new ObjectMapper();

    }

    @Test
    void getWalletBalance() throws Exception {
        mockMvc.perform(get("/api/v1/wallet/{walletId}/balance", wallet.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(wallet.getBalance()));
    }

    @SneakyThrows
    @Test
    void canDoWalletTransaction() {
        WalletTransactionRq walletTransactionRq = new WalletTransactionRq(wallet.getId(), "DEPOSIT", BigDecimal.TEN);
        String walletJson = objectMapper.writeValueAsString(walletTransactionRq);
        mockMvc.perform(post("/api/v1/wallet/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletJson))
                .andExpect(status().isOk());
        BigDecimal balance = walletRepository.getWalletBalance(walletTransactionRq.getWalletId()).orElseThrow();
        assertThat(balance).isEqualByComparingTo(BigDecimal.valueOf(510));
    }

    @SneakyThrows
    @Test
    void operationTypeShouldNotBeNull() {
        WalletTransactionRq walletTransactionRq = new WalletTransactionRq(wallet.getId(), null, BigDecimal.TEN);
        String walletJson = objectMapper.writeValueAsString(walletTransactionRq);
        mockMvc.perform((post("/api/v1/wallet/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Поле operation не может быть пустым"));
    }

    @SneakyThrows
    @Test
    void walletIdShouldNotBeNull() {
        WalletTransactionRq walletTransactionRq = new WalletTransactionRq(null, "DEPOSIT", BigDecimal.TEN);
        String walletJson = objectMapper.writeValueAsString(walletTransactionRq);
        mockMvc.perform((post("/api/v1/wallet/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Поле walletId не может быть пустым"));

    }

    @SneakyThrows
    @Test
    void amountShouldNotBeNull() {
        WalletTransactionRq walletTransactionRq = new WalletTransactionRq(wallet.getId(), "DEPOSIT", null);
        String walletJson = objectMapper.writeValueAsString(walletTransactionRq);
        mockMvc.perform((post("/api/v1/wallet/"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(walletJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Поле amount не может быть пустым"));
    }
}
