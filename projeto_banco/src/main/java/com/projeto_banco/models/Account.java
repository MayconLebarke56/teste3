package com.projeto_banco.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String type;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private float extraWithdrawal;
    @Column(nullable = true)
    private float income;



    public Account() {
    }
}
