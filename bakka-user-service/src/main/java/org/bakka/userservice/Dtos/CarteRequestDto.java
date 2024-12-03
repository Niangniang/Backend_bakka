package org.bakka.userservice.Dtos;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.CompteBakka;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarteRequestDto {

    private String reference;
    private String numero;
    private String cvv;
    private LocalDateTime dateExpiration;
    private boolean physique;
    //private CompteBakka compteBakka;
}
