package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class Carte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String reference ;
    private String numero;

    private String cvv;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dateExpiration;

    private boolean physique = false;

    @Transient
    public int getAnneeCreationPlusUn() {
        return dateExpiration.getYear() + 1;
    }
}
