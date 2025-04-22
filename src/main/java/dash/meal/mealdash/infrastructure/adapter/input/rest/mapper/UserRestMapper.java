package dash.meal.mealdash.infrastructure.adapter.input.rest.mapper;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.SignupRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRestMapper {
    MealDashUser map(SignupRequest signupRequest);
}
