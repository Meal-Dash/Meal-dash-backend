package dash.meal.mealdash.domain.service.notification.email;

import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.domain.model.Otp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService implements EmailUseCase {

    @Override
    public void sendOtp(Otp otp, String userName) {
        Context context = new Context();
        context.setVariable("user_name", userName);
        log.info(context.getVariable("user_name").toString());
        context.setVariable("user_email", otp.getEmail());
        log.info(context.getVariable("user_email").toString());
        context.setVariable("user_code", otp.getToken());
        log.info(context.getVariable("user_code").toString());
    }

    @Override
    public String verifyEmail(String email) {
        return "";
    }
}
