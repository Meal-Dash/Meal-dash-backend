package dash.meal.mealdash.services;

import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.domain.service.notification.email.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class EmailServiceTest {

    @InjectMocks
    private EmailService emailService;

    @Test
    void sendOtp(){
        Otp otp = new Otp();
        otp.setEmail("test@email.com");
        otp.setToken("123456");
        String userName = "JohnDoe";

        emailService.sendOtp(otp, userName);

        assertNotNull(otp);
    }

}
