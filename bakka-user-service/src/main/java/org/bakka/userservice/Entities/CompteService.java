package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Enums.TypeCompte;
import org.bakka.userservice.Utils.UtilMethods;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class CompteService extends Compte {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nomFacturier;

    private TypeCompte typeCompte;

    @PrePersist
    public void prePersit() {
        if (typeCompte == null) {
            typeCompte = TypeCompte.COMPTESERVICE;
        }
        if(getNumeroCompte().isEmpty()){
            String carac = UtilMethods.generateNumero();
            setNumeroCompte(carac);
        }
    }

}
