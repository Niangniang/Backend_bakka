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

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompteRequestDto {
    private TypeCompte typeCompte;
    private ClientBakka clientBakka;
}
