package dash.meal.mealdash.domain.model;


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
}
