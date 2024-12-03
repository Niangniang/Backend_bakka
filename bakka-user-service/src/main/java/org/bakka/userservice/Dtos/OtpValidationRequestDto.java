package org.bakka.userservice.Dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bakka.userservice.Entities.ClientBakka;

import java.time.Instant;
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OtpValidationRequestDto {

    private String otp;
    private ClientBakka clientBakka;
}
