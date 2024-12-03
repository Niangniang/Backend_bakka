package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.Profil;
import org.bakka.userservice.Entities.Role;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdministrateurResponseDto {
    private UUID id;
    private  String nom;
    private  String prenom;
    private  String telephone;
    private String username;
    private Profil profil;
    private boolean actif;
    private Role role;
    private String email;
}
