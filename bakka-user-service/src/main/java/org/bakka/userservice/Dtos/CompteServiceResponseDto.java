package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.ClientBakka;
import org.bakka.userservice.Enums.TypeCompte;

import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class CompteServiceResponseDto {
    private UUID id;
    private double solde;
    private LocalDateTime dateSolde;
    private String numeroCompte;
    private TypeCompte typeCompte;
    private LocalDateTime dateCreation;
    private ClientBakka clientBakka;
    private String nomFacturier;
}
