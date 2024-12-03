package org.bakka.userservice.Dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.Compte;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperationResponseDto {
    private UUID id;
    private Compte source;
    private Compte destination;
    private double montant;
    private double frais;
    private LocalDateTime dateOperation;
    private boolean statut;
}
