package dash.meal.mealdash.domain.validation;

public class RegexPattern {
    public static final String NAME_REGEX = "^[A-Za-z'-]+(?: [A-Za-z'-]+)*$";
    public static final String EMAIL_REGEX = "^(?!.*\\.\\.)[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    public static final String PHONE_NUMBER_REGEX = "^[0-9]{11}$";
}
