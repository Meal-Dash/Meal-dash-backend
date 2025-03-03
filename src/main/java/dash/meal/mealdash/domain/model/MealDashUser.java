package dash.meal.mealdash.domain.model;


import dash.meal.mealdash.domain.exception.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.exception.RegexPattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Builder
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


    public void validateFields(MealDashUser mealDashUser) throws MealDashUserAdapterException {
        validateFirstName(mealDashUser.getFirstName());
        validateLastName(mealDashUser.getLastName());
        validateEmail(mealDashUser.getEmail());
        validatePhoneNumber(mealDashUser.getPhoneNumber());
        validatePassword(mealDashUser.getPassword());
    }


    public void validateFirstName(String firstName) throws MealDashUserAdapterException {
        if (firstName == null || firstName.isEmpty()) throw new MealDashUserAdapterException(ErrorMessage.FIRST_NAME_MUST_BE_PROVIDED);
        if (!firstName.matches(RegexPattern.NAME_REGEX)) throw new MealDashUserAdapterException(ErrorMessage.INVALID_NAME_FORMAT);

    }

    public void validateLastName(String lastName) throws MealDashUserAdapterException {
        if (lastName == null || lastName.isEmpty()) throw new MealDashUserAdapterException(ErrorMessage.LAST_NAME_MUST_BE_PROVIDED);
        if (!lastName.matches(RegexPattern.NAME_REGEX)) throw new MealDashUserAdapterException(ErrorMessage.INVALID_NAME_FORMAT);

    }

    public void validateEmail(String email) throws MealDashUserAdapterException {
        if (email == null || email.isEmpty()) throw new MealDashUserAdapterException(ErrorMessage.EMAIL_IS_REQUIRED);
        if (!email.matches(RegexPattern.EMAIL_REGEX)) throw new MealDashUserAdapterException(ErrorMessage.INVALID_MAIL_FORMAT);
    }

    public void validatePassword(String password) throws MealDashUserAdapterException {
        if (password ==  null || password.isEmpty()) throw new MealDashUserAdapterException(ErrorMessage.PASSWORD_IS_REQUIRED);
        if (!password.matches(RegexPattern.PASSWORD_REGEX)) throw new MealDashUserAdapterException(ErrorMessage.PASSWORD_IS_INVALID);
    }

    public void validatePhoneNumber(String phoneNumber) throws MealDashUserAdapterException {
        if (phoneNumber == null || phoneNumber.isEmpty()) throw new MealDashUserAdapterException(ErrorMessage.PHONE_NUMBER_IS_REQUIRED);
        if (!phoneNumber.matches(RegexPattern.PHONE_NUMBER_REGEX)) throw new MealDashUserAdapterException(ErrorMessage.PHONE_NUMBER_IS_INVALID);
    }
}
