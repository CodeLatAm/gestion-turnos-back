package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="users")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String lastname;
    private String title;
    @Basic
    @Column(nullable = false)
    private String username; //email
    private String password;
    private String country;
    private String specialty;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private Set<HealthCenterEntity> centers = new HashSet<>();
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = RoleEntity.class, cascade = CascadeType.PERSIST)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<RoleEntity> roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ProfileEntity profile;

    public void addCenter(HealthCenterEntity center){
        centers.add(center);
    }


}
