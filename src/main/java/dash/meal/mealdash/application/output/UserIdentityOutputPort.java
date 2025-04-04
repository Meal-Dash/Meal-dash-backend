package dash.meal.mealdash.application.output;

import dash.meal.mealdash.domain.exception.UserAdapterException;

import dash.meal.mealdash.domain.model.MealDashUser;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserIdentityOutputPort {
    UserRepresentation saveUser(MealDashUser user) throws UserAdapterException;
    UserRepresentation getUserByEmail(String email) throws UserAdapterException;
    void deleteUser(String email);
}
