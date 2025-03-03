package dash.meal.mealdash.services;

import dash.meal.mealdash.application.input.user.UserUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MealDashUserServiceTest {

    @Autowired
    private UserUseCase userUseCase;

    @Test
    void testSignUp_successful(){

    }
}
