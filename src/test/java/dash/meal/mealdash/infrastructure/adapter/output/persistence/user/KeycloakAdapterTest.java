package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.UserRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class KeycloakAdapterTest {

    @Autowired
    private KeycloakAdapter keycloakAdapter;

    private static final List<String> createdUsers = new ArrayList<>();


    @AfterAll
    void tearDown() {
        for (String email : createdUsers) {
            keycloakAdapter.deleteUser(email);
            System.out.println("Deleted user: " + email);
        }
        createdUsers.clear();
    }

    @Test
    void KeycloakUserSignUp() throws MealDashUserAdapterException {
        MealDashUser user = new MealDashUser();
        user.setEmail("test12@gmail.com");
        user.setPassword("pasSW123@");
        user.setRole(UserRole.CUSTOMER);
        user.setLastName("test");
        user.setFirstName("tester");

        UserRepresentation representation = keycloakAdapter.saveUser(user);
        createdUsers.add(user.getEmail());
        assertNotNull(representation);
    }

    @Test
    void KeycloakSignupThrowExceptionWhenUserExists() throws MealDashUserAdapterException {
        MealDashUser user = new MealDashUser();
        user.setEmail("joy@gmail.com");
        user.setPassword("pasSW123@");
        user.setRole(UserRole.CUSTOMER);
        user.setLastName("Joy");
        user.setFirstName("Janet");

        keycloakAdapter.saveUser(user);
        createdUsers.add(user.getEmail());

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> keycloakAdapter.saveUser(user));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, exception.getMessage());
    }


}
