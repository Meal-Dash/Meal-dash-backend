package dash.meal.mealdash.domain.message;

public class ErrorMessage {
    public static final String USER_CANNOT_BE_NULL = "User cannot be null";
    public static final String FIRST_NAME_MUST_BE_PROVIDED = "First name is required";
    public static final String LAST_NAME_MUST_BE_PROVIDED = "Last name is required";
    public static final String INVALID_NAME_FORMAT = "Invalid name format";
    public static final String EMAIL_IS_REQUIRED = "Email is required";
    public static final String INVALID_MAIL_FORMAT = "Invalid email format";
    public static final String PASSWORD_IS_REQUIRED = "Password is required";
    public static final String PASSWORD_IS_INVALID = "Password is not valid";
    public static final String PHONE_NUMBER_IS_REQUIRED = "Phone number is required";
    public static final String PHONE_NUMBER_IS_INVALID = "Phone number is invalid";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_ALREADY_EXIST = "User already exist";
    public static final String NO_USERS_FOUND = "No users found";
    public static final String SOMETHING_WENT_WRONG = "Something went wrong while creating user in keycloak";
    public static final String OTP_NOT_FOUND = "Otp not found";
    public static final String TOKEN_IS_REQUIRED = "Token cannot be null or empty";
    public static final String OTP_EMAIL_IS_REQUIRED = "Email cannot be null or empty";
    public static final String OTP_ALREADY_EXIST = "Otp already exist";
    public static final String INVALID_USER_DETAILS = "User details cannot be empty.";
    public static final String INVALID_REGISTRATION_DETAILS = "Invalid registration details";
    public static final String INVALID_ROLE = "Role is required";
    public static final String ACCOUNT_ALREADY_ENABLED = "Account already enabled";
    public static final String ERROR_FETCHING_USER_INFORMATION = "Error fetching user information";
    public static final String USER_DOES_NOT_EXIST = "User does not exist";
    public static final String USER_PREVIOUSLY_VERIFIED = "User previously verified";
    public static final String INVALID_EMAIL_OR_PASSWORD = "Invalid email or password";
    public static final String INVALID_CODE = "Invalid code";
    public static final String OTP_EXPIRED_OR_INVALID = "Otp expired or invalid";
    public static final String EMAIL_ALREADY_EXIST = "Email already exists";
}
