package dash.meal.mealdash.infrastructure.adapter.mapper;

import dash.meal.mealdash.domain.model.User;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealDashMapper {
    UserEntity toUserEntity(User user);

    User toUser(UserEntity savedUserEntity);
}
