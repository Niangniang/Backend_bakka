package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.CompteBakka;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarteResponseDto {

    private UUID id;
    private String reference;
    private String numero;
    private String cvv;
    private LocalDateTime dateExpiration;
    private boolean physique;
    //private CompteBakka compteBakka;
}
