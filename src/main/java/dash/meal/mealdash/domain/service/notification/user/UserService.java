package dash.meal.mealdash.domain.service.notification.user;

import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.application.input.otp.OtpUseCase;
import dash.meal.mealdash.application.input.user.UserUseCase;
import dash.meal.mealdash.application.output.MealDashUserIdentityOutputPort;
import dash.meal.mealdash.application.output.UserOutputPort;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.message.SuccessMessage;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.CustomerSignupRequest;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserUseCase {
    private final UserOutputPort userOutputPort;
    private final MealDashUserIdentityOutputPort mealDashUserIdentityOutputPort;
    private final MealDashMapper mealDashMapper;
    private final OtpUseCase otpUseCase;
    private final EmailUseCase emailUseCase;


    @Override
    @Transactional
    public String signUp(MealDashUser user) throws MealDashException, MealDashUserAdapterException, OtpAdapterException {
        if (user == null) throw new MealDashException(ErrorMessage.INVALID_USER_DETAILS);
        user.validate();
        if (userOutputPort.existsByEmail(user.getEmail())) throw new MealDashException(ErrorMessage.USER_ALREADY_EXIST);

        Otp otp = otpUseCase.generateOtp(user.getEmail());
        log.info("OTP {}",otp);
        log.info("OTP: {} generated for user: {}", otp.getToken(), user.getEmail());

        UserRepresentation kcMealUser = mealDashUserIdentityOutputPort.saveUser(user);
        log.info("keycloak signed up user {}", kcMealUser);
        user.setId(kcMealUser.getId());


        MealDashUser mealDashUser = userOutputPort.save(user);
        log.info("user saved with id: {}", mealDashUser.getId());

        emailUseCase.sendOtp(otp, user.getFirstName());
        log.info("OTP sent to: {}", user.getEmail());

        return SuccessMessage.SUCCESSFUL_REGISTRATION;
    }
}
