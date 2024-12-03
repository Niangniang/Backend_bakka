package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.OtpValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpValidationRepository  extends JpaRepository<OtpValidation, UUID> {

    boolean existsByOtp(String otp);


   Optional<OtpValidation> findByOtp(String otp);
}
