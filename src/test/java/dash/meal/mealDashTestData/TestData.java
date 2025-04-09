package dash.meal.mealDashTestData;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.enums.UserRole;

public class TestData {

    public static MealDashUser buildTestUser(String email){
         MealDashUser user = new MealDashUser();
         user.setId("John123");
         user.setEmail(email);
         user.setPassword("Password1@");
         user.setFirstName("joy");
         user.setLastName("Joseph");
         user.setPhoneNumber("09018296447");
         user.setRole(UserRole.CUSTOMER);
        return user;
    }
}
