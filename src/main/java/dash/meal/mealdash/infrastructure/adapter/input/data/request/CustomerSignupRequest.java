package dash.meal.mealdash.infrastructure.adapter.input.data.request;

import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.validation.RegexPattern;
import dash.meal.mealdash.domain.model.Location;
import dash.meal.mealdash.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerSignupRequest {
    @NotBlank
    @Pattern(regexp = RegexPattern.NAME_REGEX, message = ErrorMessage.INVALID_NAME_FORMAT)
    private String firstName;
    @NotBlank
    @Pattern(regexp = RegexPattern.NAME_REGEX, message = ErrorMessage.INVALID_NAME_FORMAT)
    private String lastName;
    @NotBlank
    @Email(regexp = RegexPattern.EMAIL_REGEX, message = ErrorMessage.INVALID_MAIL_FORMAT)
    private String email;
    @NotBlank
    @Pattern(regexp = RegexPattern.PASSWORD_REGEX, message = ErrorMessage.PASSWORD_IS_INVALID)
    private String password;
    private String phoneNumber;
    private Location deliveryAddress;
    private UserRole role;
}
