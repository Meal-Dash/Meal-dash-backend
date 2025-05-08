package dash.meal.mealdash.domain.model;


import dash.meal.mealdash.domain.enums.UserRole;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import lombok.*;

import java.time.LocalDate;

import static dash.meal.mealdash.domain.validation.MealDashValidator.*;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MealDashUser {
    private String id;
//    private Location currentLocation;
    private LocalDate dateOfBirth;
    private String nextKinName;
    private String gender;
    private String nextKinNumber;
//    private Guarantor guarantor;
//    private Bank bank;
//    private Vehicle vehicle;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean emailVerified;
    private boolean enabled;
    private String createdAt;
    private String image;
    private String stateOfOrigin;
    private String maritalStatus;
    private String stateOfResidence;
    private String nationality;
    private String residentialAddress;
    private UserRole role;
    private String createdBy;
    private String alternateEmail;
    private String alternatePhoneNumber;
    private String alternateContactAddress;
    private String password;
    private String accessToken;
    private String refreshToken;
    private String newPassword;
    private String token;


    public void validate() throws UserAdapterException {
        validateFirstName(this.getFirstName());
        validateLastName(this.getLastName());
        validateEmail(this.getEmail());
        validatePhoneNumber(this.getPhoneNumber());
        validatePassword(this.getPassword());
    }

}
