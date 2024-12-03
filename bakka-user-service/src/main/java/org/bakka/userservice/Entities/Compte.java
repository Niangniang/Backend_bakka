package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Enums.TypeCompte;
import org.bakka.userservice.Utils.UtilMethods;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "compte", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"numeroCompte", "typeCompte"})
})
@ToString
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private double solde  = UtilMethods.generateSolde();
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false)
    private LocalDateTime dateSolde;

    @Column(nullable = false)
    private String numeroCompte;

    @Enumerated(EnumType.STRING)
    private TypeCompte typeCompte;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false, updatable = false)
    private LocalDateTime dateCreation ;

    @ManyToOne
    @JoinColumn(name = "clientBakkaId")
    private ClientBakka clientBakka;
}
