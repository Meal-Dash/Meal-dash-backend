package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealDashTestData.TestData;
import dash.meal.mealdash.application.output.UserOutputPort;
import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.message.ErrorMessage;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MealDashUserAdapterTest {

    @Autowired
    private UserOutputPort userOutputPort;

    private MealDashUser mealDashUser;

    @BeforeEach
    void setUp() {
        mealDashUser = TestData.buildTestUser("joy123@gmail.com");

        try {
            mealDashUser = userOutputPort.findByEmail(mealDashUser.getEmail());
            if (mealDashUser != null) {
                userOutputPort.deleteById(mealDashUser.getId());
            }
        }catch (UserAdapterException exception){
            log.info("Exception {}", exception.getMessage());
        }

    }

    @AfterAll
    void tearDown() throws UserAdapterException {
//        userOutputPort.deleteAll();
    }


    @Test
    void saveUser() throws MealDashException {
        MealDashUser savedMealDashUser = userOutputPort.save(mealDashUser);
        assertNotNull(savedMealDashUser);
        assertEquals(mealDashUser.getFirstName(), savedMealDashUser.getFirstName());
    }

    @Test
    void saveUserThrowExceptionWhenUserAlreadyExist() throws MealDashException {
        userOutputPort.save(mealDashUser);

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, exception.getMessage());
    }

    @Test
    void saveUser_throwExceptionWhenUserIsNull(){
        MealDashException exception = assertThrows(MealDashException.class, () -> userOutputPort.save(null));
        assertEquals(ErrorMessage.USER_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserFirstNameIsEmpty(){
        mealDashUser.setFirstName("");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.FIRST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserFirstNameIsNull(){
        mealDashUser.setFirstName(null);

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.FIRST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserFirstNameFormatIsInvalid(){
        mealDashUser.setFirstName("Mary123");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.INVALID_NAME_FORMAT, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserLastNameIsEmpty(){
        mealDashUser.setLastName("");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.LAST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserLastNameIsNull(){
        mealDashUser.setLastName(null);

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.LAST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserLastNameFormatIsInvalid(){
        mealDashUser.setLastName("Mary123");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.INVALID_NAME_FORMAT, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserEmailIsEmpty(){
        mealDashUser.setEmail("");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserEmailIsNull(){
        mealDashUser.setEmail(null);

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenUserEmailFormatIsInvalid(){
        mealDashUser.setEmail("~)(&we433");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.INVALID_MAIL_FORMAT, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPasswordIsEmpty(){
        mealDashUser.setPassword("");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPasswordIsNull(){
        mealDashUser.setPassword(null);

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPasswordIsValid_containsOnlySpecialCharacter(){
        mealDashUser.setPassword("@#$%^");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPasswordIsValid_containsOnlyNumbers(){
        mealDashUser.setPassword("12345");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPasswordIsValid_containsOnlyLowerCaseLetters(){
        mealDashUser.setPassword("abeyter");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPasswordIsValid_containsOnlyUpperCaseLetters(){
        mealDashUser.setPassword("ABYUNCE");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPasswordIsValid_lessThanEightCharacters(){
        mealDashUser.setPassword("Ab1!");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPhoneNumberIsEmpty(){
        mealDashUser.setPhoneNumber("");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPhoneNumberIsNull(){
        mealDashUser.setPhoneNumber(null);

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_REQUIRED, exception.getMessage());
    }


    @Test
    void saveUserThrowExceptionWhenPhoneNumberIsInvalid(){
        mealDashUser.setPhoneNumber("0908byu");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_INVALID, exception.getMessage());
    }

    @Test
    void saveUserThrowExceptionWhenPhoneNumberIsInvalidNumberMoreThanEleven(){
        mealDashUser.setPhoneNumber("01234567890987");

        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.save(mealDashUser));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_INVALID, exception.getMessage());
    }

    @Test
    void findUserByEmail() throws MealDashException {
        userOutputPort.save(mealDashUser);

        MealDashUser foundMealDashUser = userOutputPort.findByEmail(mealDashUser.getEmail());
        assertNotNull(foundMealDashUser);
        assertEquals(mealDashUser.getEmail(), foundMealDashUser.getEmail());
    }

    @Test
    void findUserByEmailThrowExceptionWhenUserNotFound(){
        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.findByEmail("hyu@gmail.com"));
        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void findById() throws MealDashException {
        userOutputPort.save(mealDashUser);

        MealDashUser foundMealDashUser = userOutputPort.findById(mealDashUser.getId());
        assertNotNull(foundMealDashUser);
        assertEquals(mealDashUser.getId(), foundMealDashUser.getId());
    }

    @Test
    void findUserByIdThrowExceptionWhenUserNotFound(){
        UserAdapterException exception = assertThrows(UserAdapterException.class, () -> userOutputPort.findByEmail("765@W45"));
        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void deleteUserById() throws MealDashException {
        userOutputPort.save(mealDashUser);
        userOutputPort.deleteById(mealDashUser.getId());

        assertThrows(UserAdapterException.class, () -> userOutputPort.findById(mealDashUser.getId()));
    }


    @Test
    void deleteByIdUserNotFoundThrowException() {
        String nonExistentId = "invalid123";

        Exception exception = assertThrows(UserAdapterException.class, () -> userOutputPort.deleteById(nonExistentId));

        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void findAllUsers() throws MealDashException {
        userOutputPort.save(mealDashUser);
        List<MealDashUser> users = userOutputPort.findAll();
        log.info("All users found {}", users);
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    void findAllUsersUsersNotFoundThrowException() {
        Exception exception = assertThrows(UserAdapterException.class, () -> userOutputPort.findAll());

        assertEquals(ErrorMessage.NO_USERS_FOUND, exception.getMessage());
    }

    @Test
    void existsByEmailReturnsTrue() throws MealDashException {
        userOutputPort.save(mealDashUser);

        boolean result = userOutputPort.existsByEmail(mealDashUser.getEmail());

        assertTrue(result);
    }

    @Test
    void existsByEmailReturnsFalse() throws MealDashException {
        userOutputPort.save(mealDashUser);

        boolean result = userOutputPort.existsByEmail("mealDashUser.getEmail()");

        assertFalse(result);
    }

}
