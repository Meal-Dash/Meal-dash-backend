package dash.meal.mealdash.application.input.user;

import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;

public interface UserUseCase {
    String signUp(MealDashUser user) throws MealDashException, UserAdapterException, OtpAdapterException;
}
