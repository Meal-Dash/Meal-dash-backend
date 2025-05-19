package dash.meal.mealdash.infrastructure.adapter.output.mapper;

import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.MealDashEntity;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.OtpEntity;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MealDashMapper {
    @Mapping(source = "role", target = "userRole")
    @Mapping(source = "emailVerified", target = "emailVerified")
    @Mapping(source = "enabled", target = "enabled")
    MealDashEntity toUserEntity(MealDashUser user);

    MealDashUser toUser(MealDashEntity savedMealDashEntity);

    @Mapping(source = "emailToken", target = "emailToken")
    OtpEntity toOtpEntity(Otp otp);

    @Mapping(source = "emailToken", target = "emailToken")
    Otp toOtp(OtpEntity savedOtpEntity);


    MealDashUser mapUserRepresentationToMealDashUser(UserRepresentation userRepresentation);

    @Mapping(source = "email", target = "username")
    @Mapping(source = "enabled", target = "enabled")
    @Mapping(source = "emailVerified", target = "emailVerified")
    UserRepresentation map(MealDashUser foundUser);
}
