package com.getion.turnos.repository;

import com.getion.turnos.model.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
    boolean findByUserId(Long userId);

    boolean findByUser_id(Long userId);
}
