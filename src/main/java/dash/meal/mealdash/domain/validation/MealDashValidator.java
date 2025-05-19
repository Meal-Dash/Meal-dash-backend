package dash.meal.mealdash.domain.validation;

import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class MealDashValidator {
    public static void validateObjectInstance(Object instance, String message) throws MealDashException {
        if (ObjectUtils.isEmpty(instance)){
            log.error("Object instance validation failed {}", message);
            throw new MealDashException(message);
        }
    }
    public static void validateFirstName(String firstName) throws UserAdapterException {
        if (firstName == null || firstName.isEmpty()) throw new UserAdapterException(ErrorMessage.FIRST_NAME_MUST_BE_PROVIDED);
        if (!firstName.matches(RegexPattern.NAME_REGEX)) throw new UserAdapterException(ErrorMessage.INVALID_NAME_FORMAT);

    }

    public static void validateLastName(String lastName) throws UserAdapterException {
        if (lastName == null || lastName.isEmpty()) throw new UserAdapterException(ErrorMessage.LAST_NAME_MUST_BE_PROVIDED);
        if (!lastName.matches(RegexPattern.NAME_REGEX)) throw new UserAdapterException(ErrorMessage.INVALID_NAME_FORMAT);

    }

    public static void validateEmail(String email) throws UserAdapterException {
        if (email == null || email.isEmpty()) throw new UserAdapterException(ErrorMessage.EMAIL_IS_REQUIRED);
        if (!email.matches(RegexPattern.EMAIL_REGEX)) throw new UserAdapterException(ErrorMessage.INVALID_MAIL_FORMAT);
    }

    public static void validatePassword(String password) throws UserAdapterException {
        if (password ==  null || password.isEmpty()) throw new UserAdapterException(ErrorMessage.PASSWORD_IS_REQUIRED);
        if (!password.matches(RegexPattern.PASSWORD_REGEX)) throw new UserAdapterException(ErrorMessage.PASSWORD_IS_INVALID);
    }

    public static void validatePhoneNumber(String phoneNumber) throws UserAdapterException {
        if (phoneNumber == null || phoneNumber.isEmpty()) throw new UserAdapterException(ErrorMessage.PHONE_NUMBER_IS_REQUIRED);
        if (!phoneNumber.matches(RegexPattern.PHONE_NUMBER_REGEX)) throw new UserAdapterException(ErrorMessage.PHONE_NUMBER_IS_INVALID);
    }

    public static void validateDataElement(String dataElement, String message) throws MealDashException {
        if (isEmptyString(dataElement)){
            log.error(message);
            throw new MealDashException(message);
        }
    }

    private static boolean isEmptyString(String dataElement) {
        return StringUtils.isEmpty(dataElement) || StringUtils.isBlank(dataElement);
    }
}
