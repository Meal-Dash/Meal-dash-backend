package dash.meal.mealdash.infrastructure.adapter.mapper;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.SignupRequest;
import dash.meal.mealdash.infrastructure.adapter.input.data.response.OtpResponse;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.MealDashEntity;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.OtpEntity;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface MealDashMapper {
    MealDashEntity toUserEntity(MealDashUser user);

    MealDashUser toUser(MealDashEntity savedMealDashEntity);

    UserRepresentation toUserRepresentation(SignupRequest request);

    OtpEntity toOtpEntity(Otp otp);

    Otp toOtp(OtpEntity savedOtpEntity);

    MealDashUser mapRequestToMealDash(SignupRequest signupRequest);

}
