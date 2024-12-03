package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "source_compte_id",nullable = false)
    private Compte source;
    @ManyToOne
    @JoinColumn(name = "destination_compte_id", nullable = false)
    private Compte destination;
    @Column(nullable = false)
    private double montant;
    private double frais;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false, updatable = false)
    private LocalDateTime dateOperation;
    private boolean statut;

    @PrePersist
    protected void onCreate() {
        dateOperation = LocalDateTime.now();
    }

}


