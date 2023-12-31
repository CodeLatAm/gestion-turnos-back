package com.getion.turnos.repository;

import com.getion.turnos.model.entity.HealthCenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HealthCenterRepository extends JpaRepository<HealthCenterEntity, Long> {

    Optional<HealthCenterEntity> findByNameAndAddress(String name, String address);


}
