package dev.zilvis.renginiuoaze.repository;

import dev.zilvis.renginiuoaze.enums.ERole;
import dev.zilvis.renginiuoaze.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
