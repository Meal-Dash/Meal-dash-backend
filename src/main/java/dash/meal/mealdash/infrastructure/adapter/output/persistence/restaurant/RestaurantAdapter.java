package dash.meal.mealdash.infrastructure.adapter.output.persistence.restaurant;

import dash.meal.mealdash.application.output.Restaurant.RestaurantOutputPort;
import dash.meal.mealdash.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class RestaurantAdapter implements RestaurantOutputPort {
    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurant;
    }
}
