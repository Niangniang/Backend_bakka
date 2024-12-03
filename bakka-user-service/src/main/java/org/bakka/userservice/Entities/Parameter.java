package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String libelle;
    private String valeur;

    /*
    @ManyToMany(mappedBy = "parameters")
    private Set<CompteBakka> compteBakkas;
    */

}
