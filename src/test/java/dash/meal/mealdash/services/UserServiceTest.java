package dash.meal.mealdash.services;

import dash.meal.mealDashTestData.TestData;
import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.application.input.otp.OtpUseCase;
import dash.meal.mealdash.application.output.UserIdentityOutputPort;
import dash.meal.mealdash.application.output.UserOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.message.SuccessMessage;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.domain.service.notification.user.UserService;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import org.junit.jupiter.api.BeforeEach;
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
    private UserIdentityOutputPort userIdentityOutputPort;

    @Mock
    private MealDashMapper mealDashMapper;

    @Mock
    private OtpUseCase otpUseCase;

    @Mock
    private EmailUseCase emailUseCase;
    private MealDashUser user;
    @BeforeEach
    void setUp(){
        user = TestData.buildTestUser("johndoe@gmail.com");
    }

    @Test
    void testSignup_successful() throws OtpAdapterException, MealDashException {

        Otp otp = Otp.builder()
                .token("123543")
                .email(user.getEmail())
                .createdAt(LocalDateTime.now())
                .build();

        when(userOutputPort.existsByEmail(user.getEmail())).thenReturn(false);
        when(userIdentityOutputPort.createUser(user)).thenReturn(user);
        when(userOutputPort.save(user)).thenReturn(user);
        when(otpUseCase.generateOtp(user.getEmail())).thenReturn(otp);
        doNothing().when(emailUseCase).sendOtp(otp, user.getFirstName());

        String message = userService.signUp(user);

        assertEquals(SuccessMessage.SUCCESSFUL_REGISTRATION, message);
    }

    @Test
    void testSignup_throwExceptionWhenUserExist(){

        when(userOutputPort.existsByEmail(user.getEmail())).thenReturn(true);

        MealDashException mealDashException = assertThrows(MealDashException.class, ()-> userService.signUp(user));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, mealDashException.getMessage());
    }
}
