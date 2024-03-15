package com.getion.turnos.config;

import com.getion.turnos.enums.Role;
import com.getion.turnos.model.entity.ProfileEntity;
import com.getion.turnos.model.entity.RoleEntity;
import com.getion.turnos.model.entity.UserEntity;
import com.getion.turnos.model.request.ProfileRequest;
import com.getion.turnos.repository.ProfileRepository;
import com.getion.turnos.repository.UserRepository;
import com.getion.turnos.service.injectionDependency.ProfileService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public void run(String... args) throws Exception {
        List<RoleEntity> roleEntities = new ArrayList<>();
        RoleEntity roleAdmin = new RoleEntity();
        roleAdmin.setName(Role.ADMIN);
        roleEntities.add(roleAdmin);
        if( userRepository.count() == 0) {
            UserEntity userAdmin = UserEntity.builder()

                    .name("Abel")
                    .lastname("Acevedo")
                    .specialty("Administrador")
                    .title("Administrador de la app")
                    .username("marzoa3581@gmail.com")
                    .password(passwordEncoder.encode("12345678"))
                    .country("Argentina")
                    .specialty("Admin")
                    .roles(roleEntities)
                    .build();
            // Guardar el usuario administrador en la base de datos
            userRepository.save(userAdmin);

            // Crear el perfil del usuario administrador
            ProfileEntity adminProfile = profileService.createProfile(userAdmin.getName(), userAdmin.getLastname(),
                    userAdmin.getTitle(), userAdmin.getCountry(), userAdmin.getSpecialty(), userAdmin);

            // Establecer el usuario asociado al perfil
            adminProfile.setUser(userAdmin);

            // Guardar el perfil en la base de datos
            profileRepository.save(adminProfile);
        }

    }

}
