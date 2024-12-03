package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.Banque;
import org.bakka.userservice.Entities.ClientBakka;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CompteBancaireRequestDto {
    private String numeroCompte;
    private ClientBakka clientBakka;
    private Banque banque;
}
