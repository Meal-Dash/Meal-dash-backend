package dash.meal.mealdash.domain.service.notification.user;

import dash.meal.mealdash.application.input.user.UserUseCase;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.SignupRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserUseCase {

    @Override
    public String signUp(SignupRequest signupRequest) {
        return "";
    }
}
