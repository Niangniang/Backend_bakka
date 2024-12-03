package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.Profil;
import org.bakka.userservice.Entities.Role;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AdministrateurRequestDto {

    private  String nom;
    private  String prenom;
    private  String telephone;
    private String password;
    private String email;







}
