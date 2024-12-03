package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.Role;



@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ClientBakkaRequestDto {
    private String nom;
    private String prenom;
    private String telephone;
    private String email;
    private String password;
    private String nin;
    private String addresse;
    private String biometrie;
}
