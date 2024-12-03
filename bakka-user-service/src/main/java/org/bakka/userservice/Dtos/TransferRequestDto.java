package org.bakka.userservice.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransferRequestDto {
    private UUID sourceId;
    private UUID destinationId;
    private double montant;
}
