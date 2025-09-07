package dash.meal.mealDashTestData;

import dash.meal.mealdash.domain.model.Location;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.enums.UserRole;
import dash.meal.mealdash.domain.model.Restaurant;

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
    public static Restaurant buildTestRestaurant(){
        return Restaurant.builder()
                .name("mark Eatery")
                .password("Password1@")
                .currentLocation(buildTestLocation())
                .password("JohnDoe123#")
                .build();
    }
    public static Location buildTestLocation(){
        return null;
//        return Location.builder()
//                .state("Lagos")
//                .city("Lagos")
//                .address("given address")
//                .build();
    }
}
