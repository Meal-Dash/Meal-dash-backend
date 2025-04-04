package dash.meal.mealdash.application.output;

import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;

import dash.meal.mealdash.domain.model.MealDashUser;
import org.keycloak.representations.idm.UserRepresentation;

public interface UserIdentityOutputPort {
    UserRepresentation saveUser(MealDashUser user) throws MealDashUserAdapterException;
    UserRepresentation getUserByEmail(String email) throws MealDashUserAdapterException;
    void deleteUser(String email);
}
