package dash.meal.mealdash.domain.validation;

import dash.meal.mealdash.domain.exception.MealDashException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

@Slf4j
public class MealDashValidation {
    public static void validateObjectInstance(Object instance, String message) throws MealDashException {
        if (ObjectUtils.isEmpty(instance)){
            log.error("Object instance validation failed {}", message);
            throw new MealDashException(message);
        }
    }
}
