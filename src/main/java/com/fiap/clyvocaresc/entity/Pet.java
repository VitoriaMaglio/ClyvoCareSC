package com.fiap.clyvocaresc.entity;

import com.fiap.clyvocaresc.entity.enums.PetSex;
import com.fiap.clyvocaresc.entity.enums.PetSize;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "pets")
@Data
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_users")
    @SequenceGenerator(name = "seq_users", sequenceName = "SEQ_USERS", allocationSize = 1)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private PetSex sex;

    @Column(precision = 6, scale = 3)
    private BigDecimal currentWeight;

    @Column(unique = true, length = 20)
    private String microchip;

    @Column(length = 100)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private PetSize size;

    @Column(length = 500)
    private String photoUrl;

    @Column(nullable = false)
    private LocalDate registeredAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "species_id", nullable = false)
    private Species species;
}
