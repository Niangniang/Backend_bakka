package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;


@EqualsAndHashCode(callSuper = false)
@ToString
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Administrateur extends  User implements  UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Getter
    @ManyToOne
    private Profil profil;
    private  String password;



    public Administrateur(UUID id, String nom, String prenom, String telephone, String username, String email, Boolean actif, Role role,
                          String password, Profil profil) {
        super(nom, prenom, telephone, username, email, actif, role);
        this.id = id;
        this.profil = profil;
        this.password = password;

    }


    public void setProfil(Profil profil) {
        this.profil = profil;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.getRole().getTypeRole()));
    }


/*
    @Override
    public String getUsername() {
        return this.getUsername();
    }
*/

    @Override
    public boolean isAccountNonExpired() {
        return this.isActif();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActif();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isActif();
    }

    @Override
    public boolean isEnabled() {
        return this.isActif();
    }


}