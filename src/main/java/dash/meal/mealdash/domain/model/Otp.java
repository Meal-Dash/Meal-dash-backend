package dash.meal.mealdash.domain.model;

import dash.meal.mealdash.domain.exception.ErrorMessage;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Otp {
    private String id;

    private String token;

    private String email;

    private String emailToken;

    private LocalDateTime createdAt;

    public void validations(Otp otp) throws OtpAdapterException {
        validateToken(otp.token);
        validateEmail(otp.email);
    }

    public void validateToken(String token) throws OtpAdapterException {
        if(token == null || token.isEmpty()) throw new OtpAdapterException(ErrorMessage.TOKEN_IS_REQUIRED);
    }

    public void validateEmail(String email) throws OtpAdapterException {
        if(email == null || email.isEmpty()) throw new OtpAdapterException(ErrorMessage.OTP_EMAIL_IS_REQUIRED);
    }

}
