package dash.meal.mealdash.application.output;

import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.SignupRequest;
import org.keycloak.representations.idm.UserRepresentation;

public interface MealDashUserIdentityOutputPort {
    UserRepresentation signUpUser(SignupRequest request) throws MealDashUserAdapterException;
    UserRepresentation getUserByEmail(String email) throws MealDashUserAdapterException;
    void deleteUser(String email);
}
