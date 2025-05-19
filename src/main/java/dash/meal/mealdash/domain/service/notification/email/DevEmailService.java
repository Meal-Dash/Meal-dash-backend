package dash.meal.mealdash.domain.service.notification.email;

import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.domain.model.Otp;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.time.LocalDate;

@Service
@Slf4j
@AllArgsConstructor
@Profile("default")
public class DevEmailService implements EmailUseCase {

    @Override
    public void sendOtp(Otp otp, String username) {
        Context context = new Context();
        context.setVariable("user_name", username);
        context.setVariable("user_code", otp.getToken());
        context.setVariable("user_email", otp.getEmail());
        context.setVariable("year", LocalDate.now().getYear());

    }

}
