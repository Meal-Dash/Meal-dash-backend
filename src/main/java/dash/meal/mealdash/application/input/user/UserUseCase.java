package dash.meal.mealdash.application.input.user;

import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.CustomerSignupRequest;

public interface UserUseCase {
    String signUp(MealDashUser user) throws MealDashException, MealDashUserAdapterException, OtpAdapterException;
}
