package org.bakka.userservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table
@ToString
@Entity
public class OtpValidation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String otp;
    private  Instant expiration;
    private boolean isVerify = false;
    @ManyToOne(cascade = CascadeType.ALL)
    private ClientBakka clientBakka;


    public void setExpirationWithFiveMinutesAdded() {
        this.expiration = Instant.now().plus(5, ChronoUnit.MINUTES);
    }
}
