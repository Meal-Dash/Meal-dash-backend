package dash.meal.mealdash.services;

import dash.meal.mealDashTestData.TestData;
import dash.meal.mealdash.application.input.email.EmailUseCase;
import dash.meal.mealdash.application.input.otp.OtpUseCase;
import dash.meal.mealdash.application.output.user.UserIdentityOutputPort;
import dash.meal.mealdash.application.output.user.UserOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.OtpAdapterException;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.message.SuccessMessage;
import dash.meal.mealdash.domain.model.MealDashUser;
import dash.meal.mealdash.domain.model.Otp;
import dash.meal.mealdash.domain.service.notification.user.UserService;
import dash.meal.mealdash.infrastructure.adapter.output.mapper.MealDashMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;
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

    private Otp otp;

    @BeforeEach
    void setUp(){
        user = TestData.buildTestUser("johndoe@gmail.com");

        otp = Otp.builder()
                .token("123543")
                .email(user.getEmail())
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void signup() throws OtpAdapterException, MealDashException {
        when(userOutputPort.existsByEmail(user.getEmail())).thenReturn(false);
        when(userIdentityOutputPort.createUser(user)).thenReturn(user);
        when(userOutputPort.save(user)).thenReturn(user);
        when(otpUseCase.generateOtp(user.getEmail())).thenReturn(otp);
        doNothing().when(emailUseCase).sendOtp(otp, user.getFirstName());

        String message = userService.signUp(user);

        assertEquals(SuccessMessage.SUCCESSFUL_REGISTRATION, message);
    }

    @Test
    void signupAndThrowExceptionWhenUserExist(){

        when(userOutputPort.existsByEmail(user.getEmail())).thenReturn(true);

        MealDashException mealDashException = assertThrows(MealDashException.class, ()-> userService.signUp(user));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, mealDashException.getMessage());
    }

    @Test
    void verifyUserEmail() throws MealDashException {
        when(otpUseCase.findByEmailAndToken(user.getEmail() + user.getToken())).thenReturn(otp);
        when(userOutputPort.findByEmail(otp.getEmail())).thenReturn(user);
        doNothing().when(otpUseCase).deleteOtp(otp);
        when(userIdentityOutputPort.enableUserAccount(user)).thenReturn(new MealDashUser());
        when(userOutputPort.save(user)).thenReturn(new MealDashUser());

        String successMessage = userService.verifyEmail(user);
        assertNotNull(successMessage);
        assertEquals(SuccessMessage.EMAIL_VERIFIED_SUCCESSFULLY, successMessage);
    }

    @Test
    void verifyUserEmailThrowExceptionWhenUserIsNull() throws MealDashException {
        when(otpUseCase.findByEmailAndToken(user.getEmail() + user.getToken())).thenReturn(otp);
        when(userOutputPort.findByEmail(otp.getEmail())).thenReturn(null);

       MealDashException exception = assertThrows(MealDashException.class, ()-> userService.verifyEmail(user));
       assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void shouldThrowExceptionWhenOtpIsExpired() {
        MealDashUser user = new MealDashUser();
        user.setEmail("user@example.com");
        user.setToken("someToken");

        Otp expiredOtp = new Otp();
        expiredOtp.setEmail(user.getEmail());
        expiredOtp.setToken(user.getToken());
        expiredOtp.setCreatedAt(LocalDateTime.now(ZoneOffset.UTC).minusMinutes(6));

        when(otpUseCase.findByEmailAndToken(user.getEmail() + user.getToken()))
                .thenReturn(expiredOtp);

        MealDashException exception = assertThrows(MealDashException.class, () -> {
            userService.verifyEmail(user);
        });

        assertEquals(ErrorMessage.OTP_EXPIRED_OR_INVALID, exception.getMessage());
    }

}
