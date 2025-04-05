package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.enums.UserRole;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
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
    private MealDashUser mealDashUser;


    @BeforeEach
    void setUp() {
        mealDashUser = MealDashUser.builder()
                .password("pasSW123@")
                .phoneNumber("09123456789")
                .firstName("tester")
                .lastName("test")
                .email("tester@gmail.com")
                .role(UserRole.CUSTOMER)
                .build();
    }

    @AfterAll
    void tearDown() {
        for (String email : createdUsers) {
            keycloakAdapter.deleteUser(email);
            System.out.println("Deleted user: " + email);
        }
        createdUsers.clear();
    }

    @Test
    void KeycloakUserSignUp() throws UserAdapterException {
        UserRepresentation representation = keycloakAdapter.saveUser(mealDashUser);
        createdUsers.add(mealDashUser.getEmail());
        assertNotNull(representation);
    }

    @Test
    void KeycloakSignupThrowExceptionWhenUserExists() throws UserAdapterException {
        keycloakAdapter.saveUser(mealDashUser);
        createdUsers.add(mealDashUser.getEmail());

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> keycloakAdapter.saveUser(mealDashUser));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, exception.getMessage());
    }

    @Test
    void verifyEmail(){
        keycloakAdapter.verifyEmail(mealDashUser.getEmail());


    }


}
