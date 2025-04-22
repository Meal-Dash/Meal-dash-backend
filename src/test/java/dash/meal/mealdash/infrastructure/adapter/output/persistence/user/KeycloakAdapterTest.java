package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealDashTestData.TestData;
import dash.meal.mealdash.application.output.UserIdentityOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.model.MealDashUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
class KeycloakAdapterTest {

    @Autowired
    private UserIdentityOutputPort identityOutputPort;

    private MealDashUser mealDashUser;
    private final String password = "P@ssw0rd4Test";
    private final String newPassword = "neWpasswordJ@345";
    private boolean enabled;


    @BeforeEach
    void setUp() {
        mealDashUser = TestData.buildTestUser("tester12@gmail.com");

    }

    @Test
    @Order(1)
    void createUser() {
        try {
            MealDashUser user = identityOutputPort.createUser(mealDashUser);
            assertNotNull(user);
            assertNotNull(user.getId());
            assertEquals(mealDashUser.getId(), user.getId());
            assertEquals(user.getEmail(), mealDashUser.getEmail());
            assertEquals(user.getFirstName(), mealDashUser.getFirstName());
            assertEquals(user.getLastName(), mealDashUser.getLastName());
        }catch (MealDashException exception){
            log.error("Failed to create user in keycloak", exception);
            log.info("{} {}", exception.getClass().getName(),exception.getMessage());
        }

    }

    @Test
    void createUserWithNullMeadUserUser(){
       MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createUser(null));
       assertEquals(ErrorMessage.USER_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    void createUserWithExistingEmail(){
        MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createUser(mealDashUser));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, exception.getMessage());
    }

    @Test
    void createUserWithNullEmail(){
        mealDashUser.setEmail(null);
       MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createUser(mealDashUser));
       assertEquals(ErrorMessage.INVALID_REGISTRATION_DETAILS, exception.getMessage());
    }

    @Test
    void createUserWithEmptyStringEmail(){
        mealDashUser.setEmail("");
        MealDashException mealDashException = assertThrows(MealDashException.class, () -> identityOutputPort.createUser(mealDashUser));
        assertEquals(ErrorMessage.INVALID_REGISTRATION_DETAILS, mealDashException.getMessage());
    }

    @Test
    void createUserWithInvalidRole(){
        mealDashUser.setRole(null);
        MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createUser(mealDashUser));
        assertEquals(ErrorMessage.INVALID_REGISTRATION_DETAILS, exception.getMessage());
    }

    @Test
    void createUserWithEmptyFirstName(){
        mealDashUser.setFirstName(null);
        MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createUser(mealDashUser));
        assertEquals(ErrorMessage.INVALID_REGISTRATION_DETAILS, exception.getMessage());
    }

    @Test
    void createUserWithEmptyLastName(){
        mealDashUser.setLastName(null);
        MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createUser(mealDashUser));
        assertEquals(ErrorMessage.INVALID_REGISTRATION_DETAILS, exception.getMessage());
    }

    @Test
    void createUserWithEmptyPhoneNumber(){
        mealDashUser.setPhoneNumber(null);
        MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createUser(mealDashUser));
        assertEquals(ErrorMessage.INVALID_REGISTRATION_DETAILS, exception.getMessage());
    }

    @Test
    @Order(2)
    void createPassword(){
        try {
            Optional<MealDashUser> existingUser = identityOutputPort.getUserByEmail(mealDashUser.getEmail());
            assertTrue(existingUser.isPresent());
            assertFalse(existingUser.get().isEnabled());
            assertFalse(existingUser.get().isEmailVerified());

            mealDashUser.setPassword(password);
            mealDashUser.setEmail(mealDashUser.getEmail());
            MealDashUser user = identityOutputPort.createPassword(mealDashUser);

            assertNotNull(user);
            assertNotNull(user.getId());
            assertTrue(user.isEmailVerified());
            assertTrue(user.isEnabled());
            user.setPassword(mealDashUser.getPassword());
            enabled = user.isEnabled();

            AccessTokenResponse accessTokenResponse = identityOutputPort.login(user);
            assertNotNull(accessTokenResponse);
            assertNotNull(accessTokenResponse.getToken());
            assertNotNull(accessTokenResponse.getRefreshToken());
        }catch (MealDashException exception){
            log.error("{} {}",exception.getClass().getName(),exception.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"passwordJ@345   ", "    passwordJ345"})
    void createPasswordWithSpaces(String password){
        try {
            mealDashUser.setEmail(mealDashUser.getEmail());
            MealDashUser user = identityOutputPort.createPassword(mealDashUser);
            assertNotNull(user);
            assertNotNull(user.getId());
            assertTrue(user.isEmailVerified());
            assertTrue(user.isEnabled());
            user.setPassword(password);

            AccessTokenResponse accessTokenResponse = identityOutputPort.login(user);
            assertNotNull(accessTokenResponse);
            assertNotNull(accessTokenResponse.getToken());
            assertNotNull(accessTokenResponse.getRefreshToken());
        }catch (MealDashException exception){
            log.error("Failed to create password", exception);
        }
    }

    @Test
    @Order(3)
    void login(){
        try {
            mealDashUser.setEmail(mealDashUser.getEmail());
            mealDashUser.setPassword(password);
            identityOutputPort.createPassword(mealDashUser);

            AccessTokenResponse accessTokenResponse = identityOutputPort.login(mealDashUser);
            assertNotNull(accessTokenResponse);
            assertNotNull(accessTokenResponse.getToken());
            assertNotNull(accessTokenResponse.getRefreshToken());
        }catch (MealDashException exception){
            log.error("Error logging in user {}", exception.getMessage());
        }
    }

    @Test
    void loginWithValidEmailAddressAndInvalidPassword(){
        mealDashUser.setPassword("invalid-password");
        MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createPassword(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());

    }

    @Test
    void loginWithInvalidEmailAddressAndValidPassword(){
        mealDashUser.setEmail("invalid-email");
        MealDashException exception = assertThrows(MealDashException.class, () -> identityOutputPort.createPassword(mealDashUser));
        assertEquals(ErrorMessage.INVALID_MAIL_FORMAT, exception.getMessage());

    }

    @ParameterizedTest
    @ValueSource(strings = {StringUtils.EMPTY})
    void loginWithNullPassword(String password) {
        mealDashUser.setPassword(password);
        MealDashException mealDashException = assertThrows(MealDashException.class, () ->
                identityOutputPort.login(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_REQUIRED, mealDashException.getMessage());
    }

    @Test
    void changePasswordWithNull() {
        assertThrows(MealDashException.class, () -> identityOutputPort.setPassword(null));
    }

    @Test
    void changePasswordWithNullNewPassword() {
        mealDashUser.setNewPassword(null);
        assertThrows(MealDashException.class, () -> identityOutputPort.setPassword(mealDashUser));
    }

    @ParameterizedTest
    @ValueSource(strings={StringUtils.EMPTY, StringUtils.SPACE, "rniejfkn", "  ADKFDJHFD", "ADKFDJHFD  ", "@ndnue90 -  f"})
    void changePasswordWithInvalidPassword(String password) {
        mealDashUser.setNewPassword(password);
        Exception exception = assertThrows(MealDashException.class, () -> identityOutputPort.setPassword(mealDashUser));
        log.info(exception.getMessage());
    }

    @Test
    @Order(4)
    void enableAccountThatHasBeenEnabled() {
        mealDashUser.setId("wERT_123");
        assertThrows(MealDashException.class, () -> identityOutputPort.enableUserAccount(mealDashUser));
    }

    @Test
    void enableAccountWithNull() {
        assertThrows(MealDashException.class, () -> identityOutputPort.enableUserAccount(null));
    }

    @Test
    void enableAccountWithNonExistingEmail() {
        mealDashUser.setEmail("nonexisting@gmail.com");
        assertThrows(MealDashException.class, () -> identityOutputPort.enableUserAccount(mealDashUser));
    }

    @ParameterizedTest
    @ValueSource(strings = {StringUtils.EMPTY, StringUtils.SPACE, "ebuefh", " osisiogubjh@mailinator.com"})
    void enableAccountWithInvalidEmail(String email) {
        mealDashUser.setEmail(email);
        Exception exception = assertThrows(MealDashException.class, () -> identityOutputPort.enableUserAccount(mealDashUser));
        log.info(exception.getMessage());
    }

    @Test
    void deleteUser() {
        try {
            UserRepresentation userRepresentation = identityOutputPort.getUserRepresentation(mealDashUser, Boolean.TRUE);
            mealDashUser.setId(userRepresentation.getId());
            identityOutputPort.deleteUser(mealDashUser);
        } catch (MealDashException exception) {
            log.info(exception.getMessage());
        }
        assertThrows(MealDashException.class, ()-> identityOutputPort.getUserRepresentation(mealDashUser, Boolean.TRUE));
    }

    @Test
    void deleteUserWithNullUserIdentity() {
        assertThrows(MealDashException.class,()-> identityOutputPort.deleteUser(null));
    }

    @Test
    void deleteUserWithNullUserId() {
        assertThrows(MealDashException.class,()-> identityOutputPort.deleteUser(mealDashUser));
    }

    @Test
    void deleteUserWithInCorrectUserId() {
        mealDashUser.setId("incorrect user id");
        assertThrows(MealDashException.class,()-> identityOutputPort.deleteUser(mealDashUser));
    }


}
