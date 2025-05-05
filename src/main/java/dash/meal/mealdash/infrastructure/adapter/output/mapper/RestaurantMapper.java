package dash.meal.mealdash.infrastructure.adapter.output.mapper;

import dash.meal.mealdash.domain.model.Restaurant;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.RestaurantEntity;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface RestaurantMapper {
    RestaurantEntity map(Restaurant restaurant);

    Restaurant map(RestaurantEntity savedRestaurant);
}
