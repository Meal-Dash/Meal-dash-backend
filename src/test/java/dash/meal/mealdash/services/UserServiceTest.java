package dash.meal.mealdash.services;

import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.application.input.otp.OtpUseCase;
import dash.meal.mealdash.application.output.MealDashUserIdentityOutputPort;
import dash.meal.mealdash.application.output.UserOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.message.SuccessMessage;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.domain.model.UserRole;
import dash.meal.mealdash.domain.service.notification.user.UserService;
import dash.meal.mealdash.infrastructure.adapter.input.data.request.SignupRequest;
import dash.meal.mealdash.infrastructure.adapter.mapper.MealDashMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserOutputPort userOutputPort;

    @Mock
    private MealDashUserIdentityOutputPort mealDashUserIdentityOutputPort;

    @Mock
    private MealDashMapper mealDashMapper;

    @Mock
    private OtpUseCase otpUseCase;

    @Mock
    private EmailUseCase emailUseCase;

    @Test
    void testSignup_successful() throws MealDashUserAdapterException, OtpAdapterException, MealDashException {
        SignupRequest request = SignupRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .password("Pass123#$")
                .role(UserRole.CUSTOMER)
                .phoneNumber("09182954673")
                .build();
        MealDashUser mealDashUser = MealDashUser.builder()
                .phoneNumber(request.getPhoneNumber())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(UserRole.CUSTOMER)
                .build();

        Otp otp = Otp.builder()
                .token("123543")
                .email(request.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        when(userOutputPort.existsByEmail(request.getEmail())).thenReturn(false);
        when(mealDashUserIdentityOutputPort.saveUser(request)).thenReturn(new UserRepresentation());
        when(mealDashMapper.mapRequestToMealDash(request)).thenReturn(mealDashUser);
        when(userOutputPort.save(mealDashUser)).thenReturn(mealDashUser);
        when(otpUseCase.generateOtp(request.getEmail())).thenReturn(otp);
        doNothing().when(emailUseCase).sendOtp(otp, request.getFirstName());

        String message = userService.signUp(request);

        assertEquals(SuccessMessage.SUCCESSFUL_REGISTRATION, message);
    }

    @Test
    void testSignup_throwExceptionWhenUserExist(){
        SignupRequest request = SignupRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@doe.com")
                .password("Pass123#$")
                .role(UserRole.CUSTOMER)
                .phoneNumber("09182954673")
                .build();

        when(userOutputPort.existsByEmail(request.getEmail())).thenReturn(true);

        MealDashException mealDashException = assertThrows(MealDashException.class, ()-> userService.signUp(request));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, mealDashException.getMessage());
    }
}
