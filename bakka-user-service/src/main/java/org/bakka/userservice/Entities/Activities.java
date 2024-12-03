package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Table
@ToString
@Entity
@Data
public class Activities {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private LocalDateTime dateHeure;
    private String Action;
    @ManyToOne
    private Administrateur administrateur;
    @ManyToOne
    private CompteBakka compteBakka;

}
