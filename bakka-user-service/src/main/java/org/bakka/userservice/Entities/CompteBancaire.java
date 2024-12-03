package org.bakka.userservice.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class CompteBancaire extends Compte {
    @ManyToOne
    @JoinColumn(name = "banqueId")
    private Banque banque;
}
