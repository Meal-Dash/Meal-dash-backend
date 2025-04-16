package dash.meal.mealdash.domain.service.notification.user;

import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.application.input.otp.OtpUseCase;
import dash.meal.mealdash.application.input.user.UserUseCase;
import dash.meal.mealdash.application.output.UserIdentityOutputPort;
import dash.meal.mealdash.application.output.UserOutputPort;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.message.SuccessMessage;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserUseCase {
    private final UserOutputPort userOutputPort;
    private final UserIdentityOutputPort userIdentityOutputPort;
    private final MealDashMapper mealDashMapper;
    private final OtpUseCase otpUseCase;
//    @Qualifier("mailgunEmailService")
    private final EmailUseCase emailUseCase;


    @Override
    public String signUp(MealDashUser user) throws MealDashException, UserAdapterException, OtpAdapterException {
        if (user == null) throw new MealDashException(ErrorMessage.INVALID_USER_DETAILS);
        user.validate();
        if (userOutputPort.existsByEmail(user.getEmail())) throw new MealDashException(ErrorMessage.USER_ALREADY_EXIST);

        Otp otp = otpUseCase.generateOtp(user.getEmail());
        log.info("OTP {}",otp);
        log.info("OTP: {} generated for user: {}", otp.getToken(), user.getEmail());

        UserRepresentation kcMealUser = userIdentityOutputPort.saveUser(user);
        log.info("keycloak signed up user {}", kcMealUser);
        user.setId(kcMealUser.getId());


        MealDashUser mealDashUser = userOutputPort.save(user);
        log.info("user saved with id: {}", mealDashUser.getId());

        emailUseCase.sendOtp(otp, user.getFirstName());
        log.info("OTP sent to: {}", user.getEmail());

        return SuccessMessage.SUCCESSFUL_REGISTRATION;
    }
}
