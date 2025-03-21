package dash.meal.mealdash.infrastructure.adapter.mapper;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.infrastructure.adapter.input.rest.data.request.SignupRequest;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.MealDashEntity;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealDashMapper {
    MealDashEntity toUserEntity(MealDashUser user);

    MealDashUser toUser(MealDashEntity savedMealDashEntity);

    UserRepresentation toUserRepresentation(SignupRequest request);
}
