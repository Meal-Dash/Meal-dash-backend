package dash.meal.mealDashTestData;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.enums.UserRole;

public class TestData {

    public static MealDashUser buildTestUser(String email){
        return MealDashUser.builder()
                .id("John123")
                .phoneNumber("09018296447")
                .password("Password1@")
                .firstName("John")
                .lastName("Doe")
                .email(email)
                .role(UserRole.CUSTOMER)
                .build();
    }
}
