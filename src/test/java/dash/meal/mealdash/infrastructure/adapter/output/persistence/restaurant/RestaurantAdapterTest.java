package dash.meal.mealdash.infrastructure.adapter.output.persistence.restaurant;

import dash.meal.mealdash.application.output.Restaurant.RestaurantOutputPort;
import dash.meal.mealdash.domain.model.Restaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RestaurantAdapterTest {
    private Restaurant restaurant = new Restaurant();
    @Autowired
    private RestaurantOutputPort restaurantOutputPort ;

    @Test
    void onboardRestaurantSuccessful(){
        Restaurant savedRestaurant = restaurantOutputPort.save(restaurant);
        assertNotNull(savedRestaurant);
        assertNotNull(savedRestaurant);


//        assertEquals();
    }
}
