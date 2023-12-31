package com.getion.turnos.model.response;

import com.getion.turnos.model.entity.HealthCenterEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private String name;
    private String lastname;
    private String country;
    private String title;
    //private Set<HealthCenterEntity> healthCenterEntitySet;
}
