package org.bakka.acountbakkaservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class CompteBakka {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private double solde;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column( nullable = false, updatable = false)
    private Date dateCreation;
//    @OneToOne
//    @JoinColumn(name = "clientBakkaId", referencedColumnName = "id")
//    private ClientBakka clientBakka;
}
