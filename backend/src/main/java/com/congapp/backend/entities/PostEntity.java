package com.congapp.backend.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
public class PostEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private UUID account_id;

    @Column(name = "caption")
    private String caption;

    @Column(name = "providers")
    @Enumerated(value = EnumType.STRING)
    private ProviderType providerType;

    @Column(name = "phrase")
    private String phrase;

    @Column(name = "tags")
    private Set<String> tags = new HashSet<>();
}
