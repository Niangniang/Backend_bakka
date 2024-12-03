package org.bakka.userservice.Entities;


import jakarta.persistence.*;
import lombok.Data;



import java.util.UUID;



@MappedSuperclass
@Data
public abstract class User {

    private String nom;
    private String prenom;
    private String telephone;
    @Column(nullable = false,unique = true)
    private String username;
    private String email;
    private boolean actif = false;

    @ManyToOne
    private Role role;
    public User(String nom, String prenom, String telephone, String username, String email, Boolean actif, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.username = username;
        this.email = email;
        this.actif = actif;
        this.role = role;
    }

    public  User(){}

    public void setIsActive(boolean value){
        this.actif=value;
    }


}
