package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class CompteBakka extends Compte {
    @ManyToMany
    @JoinTable(
            name = "clientBakka_parameter",
            joinColumns = @JoinColumn(name = "clientBakkaId"),
            inverseJoinColumns = @JoinColumn(name = "parameterId")
    )
    private Set<Parameter> parameters;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn
    private Carte carte;


    @ManyToMany
    @JoinTable(
            name = "compte_bakka_beneficiaires",
            joinColumns = @JoinColumn(name = "compteBakkaId"),
            inverseJoinColumns = @JoinColumn(name = "beneficiaire_id")

    )
    private Set<Beneficiaire> beneficiaires; // Set of CompteBakka that are benefactors of this account

}
