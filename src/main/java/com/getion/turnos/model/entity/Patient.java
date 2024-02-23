package com.getion.turnos.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String healthInsurance; //obra social
    private String genre;
    private String affiliateNumber;
    private LocalDate dateOfBirth;
    private String cellphone;
    private String landline;
    private String nationality;
    private String province;
    private String address;
    private String profession;
    private String email;
    private String dni;
    private String plan;
    private String age;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClinicHistory> clinicHistoryList = new ArrayList<>();

    public void  addClinicHistory(ClinicHistory history){
        clinicHistoryList.add(history);
        history.setPatient(this); // Establecer la relación en ambas direcciones

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Patient{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", healthInsurance='").append(healthInsurance).append('\'');
        sb.append(", genre='").append(genre).append('\'');
        sb.append(", affiliateNumber='").append(affiliateNumber).append('\'');
        sb.append(", dateOfBirth=").append(dateOfBirth);
        sb.append(", cellphone='").append(cellphone).append('\'');
        sb.append(", landline='").append(landline).append('\'');
        sb.append(", nationality='").append(nationality).append('\'');
        sb.append(", province='").append(province).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", profession='").append(profession).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", dni='").append(dni).append('\'');
        sb.append(", plan='").append(plan).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
