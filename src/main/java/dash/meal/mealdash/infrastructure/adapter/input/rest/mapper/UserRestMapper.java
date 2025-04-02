package dash.meal.mealdash.infrastructure.adapter.input.rest.mapper;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.CustomerSignupRequest;
import jakarta.validation.Valid;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    MealDashUser map(CustomerSignupRequest customerSignupRequest);
}
