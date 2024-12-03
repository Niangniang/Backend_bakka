package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true,nullable = false)
    private String libelle;
}
