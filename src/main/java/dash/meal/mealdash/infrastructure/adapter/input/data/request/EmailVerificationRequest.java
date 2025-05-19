package dash.meal.mealdash.infrastructure.adapter.input.data.request;

import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.validation.RegexPattern;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerificationRequest {
    @NotBlank
    @Email(regexp = RegexPattern.EMAIL_REGEX, message = ErrorMessage.INVALID_MAIL_FORMAT)
    private String email;
    @NotBlank
    private String token;
}
