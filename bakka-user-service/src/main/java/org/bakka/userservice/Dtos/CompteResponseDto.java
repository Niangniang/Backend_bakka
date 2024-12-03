package org.bakka.userservice.Dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.ClientBakka;
import org.bakka.userservice.Enums.TypeCompte;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompteResponseDto {
    private UUID id;
    private double solde;
    private LocalDateTime dateSolde;
    private String numeroCompte;
    private TypeCompte typeCompte;
    private LocalDateTime dateCreation;
    private ClientBakka clientBakka;
}
