package dash.meal.mealdash.application.output.Restaurant;

import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.model.Restaurant;
import org.springframework.stereotype.Component;

public interface RestaurantOutputPort {

    Restaurant save(Restaurant restaurant);

    Restaurant findById(String restaurantId) throws MealDashException;
}
