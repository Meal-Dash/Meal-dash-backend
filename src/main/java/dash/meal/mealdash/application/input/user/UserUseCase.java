package dash.meal.mealdash.application.input.user;

import dash.meal.mealdash.infrastructure.adapter.input.rest.data.request.SignupRequest;

public interface UserUseCase {
    String signUp(SignupRequest signupRequest);
}
