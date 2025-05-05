package dash.meal.mealdash.infrastructure.adapter.output.persistence.restaurant;

import dash.meal.mealdash.application.output.Restaurant.RestaurantOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.model.Restaurant;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.RestaurantMapper;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.RestaurantEntity;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantAdapter implements RestaurantOutputPort {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;
    @Override
    public Restaurant save(Restaurant restaurant) {
        RestaurantEntity restaurantEntity = restaurantMapper.map(restaurant);
        log.info("Restaurant mapped {}", restaurantEntity);
        RestaurantEntity savedRestaurant = restaurantRepository.save(restaurantEntity);
        log.info("The saved restaurant is {}", savedRestaurant);
        return restaurantMapper.map(savedRestaurant);
    }

    @Override
    public Restaurant findById(String restaurantId) throws MealDashException {
        RestaurantEntity savedRestaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(()-> new MealDashException("Restaurant not found"));
        log.info("The restaurant found is {}", savedRestaurant);
        return restaurantMapper.map(savedRestaurant);
    }
}
