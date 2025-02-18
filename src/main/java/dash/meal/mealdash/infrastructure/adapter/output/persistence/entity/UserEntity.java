package dash.meal.mealdash.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    @OneToMany
    private List<LocationEntity> currentLocation;
    private String password;
    private LocalDate dateOfBirth;
    private String nextKinName;
    private String gender;
    private String phoneNumber;
    private String nextKinNumber;
    @OneToOne
    private GuarantorEntity guarantor;
    @ManyToOne
    private BankEntity bank;
    private boolean isIdentityVerified;
    private UserRole userRole;
    @ManyToOne
    private VehicleEntity vehicle;

}
