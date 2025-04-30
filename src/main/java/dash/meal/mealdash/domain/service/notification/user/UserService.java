package dash.meal.mealdash.domain.service.notification.user;

import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.application.input.otp.OtpUseCase;
import dash.meal.mealdash.application.input.user.UserUseCase;
import dash.meal.mealdash.application.output.user.UserIdentityOutputPort;
import dash.meal.mealdash.application.output.user.UserOutputPort;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.message.SuccessMessage;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.domain.validation.MealDashValidator;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserUseCase {
    private final UserOutputPort userOutputPort;
    private final UserIdentityOutputPort userIdentityOutputPort;
    private final MealDashMapper mealDashMapper;
    private final OtpUseCase otpUseCase;
    private final EmailUseCase emailUseCase;


    @Override
    public String signUp(MealDashUser user) throws MealDashException, OtpAdapterException {
        MealDashValidator.validateObjectInstance(user, ErrorMessage.USER_CANNOT_BE_NULL);
        user.validate();
        if (userOutputPort.existsByEmail(user.getEmail())) throw new MealDashException(ErrorMessage.USER_ALREADY_EXIST);

        Otp otp = otpUseCase.generateOtp(user.getEmail());
        log.info("OTP {}",otp);
        log.info("OTP: {} generated for user: {}", otp.getToken(), user);

        user = userIdentityOutputPort.createUser(user);
        log.info("keycloak signed up user {}", user);

        MealDashUser mealDashUser = userOutputPort.save(user);
        log.info("user saved with id: {}", mealDashUser.getId());

        emailUseCase.sendOtp(otp, user.getEmail());
        log.info("OTP sent to: {}", user.getEmail());

        return SuccessMessage.SUCCESSFUL_REGISTRATION;
    }
}
