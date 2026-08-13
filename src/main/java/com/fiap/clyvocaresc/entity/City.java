package com.fiap.clyvocaresc.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "cities")
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
    @SequenceGenerator(name = "seq_users", sequenceName = "SEQ_USERS", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 2)
    private String state;

    @Column(nullable = false, length = 20)
    private String region;
}
