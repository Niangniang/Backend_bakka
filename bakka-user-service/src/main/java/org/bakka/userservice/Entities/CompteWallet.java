package org.bakka.userservice.Entities;

import jakarta.persistence.*;
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
public class CompteWallet extends Compte{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "walletId")
    private Wallet operateur;
}
