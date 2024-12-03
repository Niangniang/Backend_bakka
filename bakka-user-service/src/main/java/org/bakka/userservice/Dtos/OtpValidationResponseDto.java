package org.bakka.userservice.Dtos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.ClientBakka;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OtpValidationResponseDto {
    private UUID id;
    private String otp;
    private  Instant expiration;
    private boolean isVerify;
    private ClientBakka clientBakka;
}
