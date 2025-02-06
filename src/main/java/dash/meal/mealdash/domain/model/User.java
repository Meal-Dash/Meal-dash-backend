package dash.meal.mealdash.domain.model;

<<<<<<< HEAD

import java.time.LocalDate;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private Location currentLocation;
    private String password;
    private LocalDate dateOfBirth;
    private String nextKinName;
    private String gender;
    private String phoneNumber;
    private String nextKinNumber;
    private Guarantor guarantor;
    private Bank bank;
    private boolean isIdentityVerified;
    private UserRole userRole;
    private Vehicle vehicle;
=======
public class User {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private boolean emailVerified;
    private boolean enabled;
    private String createdAt;
    private String image;
    private String gender;
    private String dateOfBirth;
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
>>>>>>> origin/keycloak-security
}
