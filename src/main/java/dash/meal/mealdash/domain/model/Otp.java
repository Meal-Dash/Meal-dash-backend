package dash.meal.mealdash.domain.model;

import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Otp {
    @Id
    @UuidGenerator
    private String id;

    private String token;

    private String email;

    private String emailToken;

    private LocalDateTime createdAt;

    public Otp(String token, String email, LocalDateTime createdAt) {
        this.token = token;
        this.email = email;
        this.createdAt = createdAt;
    }

    public void validations() throws OtpAdapterException {
        validateToken(this.token);
        validateEmail(this.email);
    }

    public void validateToken(String token) throws OtpAdapterException {
        if(token == null || token.isEmpty()) throw new OtpAdapterException(ErrorMessage.TOKEN_IS_REQUIRED);
    }

    public void validateEmail(String email) throws OtpAdapterException {
        if(email == null || email.isEmpty()) throw new OtpAdapterException(ErrorMessage.OTP_EMAIL_IS_REQUIRED);
    }

}
