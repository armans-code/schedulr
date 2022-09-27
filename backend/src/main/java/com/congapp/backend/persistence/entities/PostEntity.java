package com.congapp.backend.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "post")
public class PostEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private AccountEntity account;

    @Column(name = "caption")
    private String caption;

    @Column(name = "providers")
    @Enumerated(value = EnumType.STRING)
    private ProviderType providerType;

    @Column(name = "phrase")
    private String phrase;

//    @Column(name = "tags")
//    private List<String> tags = new List<String>() {
//    };
}
