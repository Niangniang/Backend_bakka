package org.bakka.userservice.Repositories;


import org.bakka.userservice.Entities.ClientBakka;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientBakkaRepository extends JpaRepository<ClientBakka, UUID> {

    Boolean existsClientBakkaByTelephone(String telephone);

    Optional<ClientBakka> findByUsername(String username);


}
