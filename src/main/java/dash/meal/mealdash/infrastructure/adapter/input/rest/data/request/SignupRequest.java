package dash.meal.mealdash.infrastructure.adapter.input.rest.data.request;

import dash.meal.mealdash.domain.model.Location;
import dash.meal.mealdash.domain.model.UserRole;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private Location deliveryAddress;
    private UserRole role;
}
