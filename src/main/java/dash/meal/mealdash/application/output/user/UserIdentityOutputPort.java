package dash.meal.mealdash.application.output.user;

import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.UserAdapterException;

import dash.meal.mealdash.domain.model.MealDashUser;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.Optional;

public interface UserIdentityOutputPort {
    MealDashUser createUser(MealDashUser user) throws MealDashException;
    Optional<MealDashUser> getUserByEmail(String email) throws UserAdapterException;
    void deleteUser(MealDashUser user) throws MealDashException;
    MealDashUser enableUserAccount(MealDashUser user) throws MealDashException;
    MealDashUser createPassword(MealDashUser user) throws MealDashException;
    void setPassword(MealDashUser user) throws MealDashException;
    AccessTokenResponse login(MealDashUser user) throws MealDashException;
    UserRepresentation getUserRepresentation(MealDashUser user, boolean exactMatch) throws MealDashException;
}
