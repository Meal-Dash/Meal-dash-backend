package dash.meal.mealdash.infrastructure.adapter.output.persistence.restaurant;

import dash.meal.mealdash.application.output.Restaurant.RestaurantOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.model.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static dash.meal.mealDashTestData.TestData.buildTestRestaurant;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RestaurantAdapterTest {
    private final Restaurant restaurant = buildTestRestaurant();
    private String restaurantId ;
    @Autowired
    private RestaurantOutputPort restaurantOutputPort ;

    @Test
    @Order(1)
    void onboardRestaurantSuccessful(){
        Restaurant savedRestaurant = restaurantOutputPort.save(restaurant);
        assertNotNull(savedRestaurant);
        assertNotNull(savedRestaurant.getId());
        restaurantId = savedRestaurant.getId();
    }
    @Test
    @Order(2)
    void findRestaurantById(){
        Restaurant foundRestaurant = null;
        try {
            foundRestaurant = restaurantOutputPort.findById(restaurantId);
            log.info("Restaurant in adapter test found {}", foundRestaurant);
        } catch (MealDashException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(foundRestaurant);
        assertNotNull(foundRestaurant.getId());
        assertEquals(foundRestaurant.getId(), restaurantId);
        assertNotNull(foundRestaurant.getName());
        assertEquals(foundRestaurant.getName(), restaurant.getName());
    }
    @ParameterizedTest
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE})
    void findWithInvalidId(String id){
        assertThrows(MealDashException.class, ()-> restaurantOutputPort.findById(id));
    }
}
