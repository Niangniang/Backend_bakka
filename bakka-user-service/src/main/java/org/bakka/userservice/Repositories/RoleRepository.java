package org.bakka.userservice.Repositories;

import org.bakka.userservice.Entities.Role;
import org.bakka.userservice.Enums.TypeRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByTypeRole(TypeRole roleType);
}
