package com.arkaces.aces_marketplace_api.account;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "api_keys")
public class ApiKeyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

    private String id;

    private String key;

    @ManyToOne
    private AccountEntity accountEntity;

}
