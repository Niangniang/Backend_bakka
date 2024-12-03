package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.Compte;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperationRequestDto {
    private Compte source;
    private Compte destination;
    private double montant;
    private double frais;
    private boolean statut;
}
