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
public class ClientBakka extends User implements  UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String  nin;
    private String addresse;
    private String biometrie;
    public String password;

   
    public ClientBakka(UUID id, String nom, String prenom, String telephone, String username,  String email,Boolean actif, Role role,
                          String password, String nin, String addresse, String biometrie) {
        super(nom, prenom, telephone, username, email, actif, role);
        this.id = id;
        this.nin = nin;
        this.addresse = addresse;
        this.biometrie = biometrie;
        this.password = password;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + this.getRole().getTypeRole()));
    }


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
