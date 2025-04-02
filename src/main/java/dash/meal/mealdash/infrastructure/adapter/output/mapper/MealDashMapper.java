package dash.meal.mealdash.infrastructure.adapter.output.mapper;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.CustomerSignupRequest;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.MealDashEntity;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.OtpEntity;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MealDashMapper {
    MealDashEntity toUserEntity(MealDashUser user);

    MealDashUser toUser(MealDashEntity savedMealDashEntity);

    UserRepresentation toUserRepresentation(MealDashUser user);

    OtpEntity toOtpEntity(Otp otp);

    Otp toOtp(OtpEntity savedOtpEntity);


}
