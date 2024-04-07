package com.example.task_project.walletApp;


import com.example.task_project.PersonApp.PersonEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "wallet")
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class WalletEntity {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @OneToOne(fetch = FetchType.LAZY)
    private PersonEntity person;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    public WalletEntity(PersonEntity person, BigDecimal balance) {
        this.person = person;
        this.balance = balance;
    }
}
