package org.bakka.userservice.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BeneficiaireRequestDto {

    private String nom;

    private String prenom;

    private String numeroCompte;
    private UUID compteBakkaId;
    private String telephone;


}