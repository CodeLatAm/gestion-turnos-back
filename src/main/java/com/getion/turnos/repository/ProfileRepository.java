package com.getion.turnos.repository;

import com.getion.turnos.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {


    Optional<ProfileEntity> findByUser_id(Long userId);


    boolean existsByUser_Id(Long id);
}
