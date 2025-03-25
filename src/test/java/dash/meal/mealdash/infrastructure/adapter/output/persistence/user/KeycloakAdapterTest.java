package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.domain.exception.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.model.UserRole;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.SignupRequest;
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
    void testKeycloakUserSignUp_successful() throws MealDashUserAdapterException {
        SignupRequest request = new SignupRequest();
        request.setEmail("test12@gmail.com");
        request.setPassword("pasSW123@");
        request.setRole(UserRole.CUSTOMER);
        request.setLastName("test");
        request.setFirstName("tester");

        UserRepresentation representation = keycloakAdapter.signUpUser(request);
        createdUsers.add(request.getEmail());
        assertNotNull(representation);
    }

    @Test
    void testKeycloakSignup_throwExceptionWhenUserExists() throws MealDashUserAdapterException {
        SignupRequest request = new SignupRequest();
        request.setEmail("joy@gmail.com");
        request.setPassword("pasSW123@");
        request.setRole(UserRole.CUSTOMER);
        request.setLastName("Joy");
        request.setFirstName("Janet");

        keycloakAdapter.signUpUser(request);
        createdUsers.add(request.getEmail());

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> keycloakAdapter.signUpUser(request));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, exception.getMessage());
    }


}
