package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.Administrateur;
import org.bakka.userservice.Entities.CompteBakka;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ActivitiesResponseDto {
    private UUID id;
    private LocalDateTime dateHeure;
    private String Action;
    private Administrateur administrateur;
    private CompteBakka compteBakka;
}
