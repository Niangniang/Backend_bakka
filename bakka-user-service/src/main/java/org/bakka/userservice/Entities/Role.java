package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.bakka.userservice.Enums.TypeRole;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private TypeRole typeRole;


}
