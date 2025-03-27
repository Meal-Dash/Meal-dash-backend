package dash.meal.mealDashTestData;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.UserRole;

public class TestData {

    public static MealDashUser buildTestUser(String email){
        return MealDashUser.builder()
                .id("John123")
                .firstName("Joy")
                .lastName("Joseph")
                .email(email)
                .password("Password1@")
                .phoneNumber("09018296447")
                .role(UserRole.CUSTOMER)
                .build();
    }
}
