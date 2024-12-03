package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.CompteService;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NumeroServiceResponseDto {
    private UUID id;
    private String referrence;
    private CompteService compteService;
}
