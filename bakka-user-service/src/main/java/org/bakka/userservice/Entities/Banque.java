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
public class Banque {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String intitule;
    private String imageUrl;
}
