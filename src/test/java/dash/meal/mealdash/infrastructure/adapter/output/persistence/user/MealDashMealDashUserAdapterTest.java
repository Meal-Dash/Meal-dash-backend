package dash.meal.mealdash.infrastructure.adapter.output.persistence.user;

import dash.meal.mealdash.domain.exception.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
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
class MealDashMealDashUserAdapterTest {

    @Autowired
    private MealDashUserAdapter mealDashUserAdapter;

    private MealDashUser mealDashUser;

    @BeforeEach
    void setUp() {
        mealDashUser = MealDashUser.builder()
                .id("John123")
                .firstName("Joy")
                .lastName("Joseph")
                .email("joy123@gmail.com")
                .password("Password1@")
                .phoneNumber("09018296447")
                .build();

        try {
            mealDashUser = mealDashUserAdapter.findByEmail(mealDashUser.getEmail());
            if (mealDashUser != null) {
                mealDashUserAdapter.deleteById(mealDashUser.getId());
            }
        }catch (MealDashUserAdapterException exception){
            log.info("Exception {}", exception.getMessage());
        }

    }

    @AfterAll
    void tearDown() {
        if (mealDashUser != null) {
            try {
                mealDashUserAdapter.deleteById(mealDashUser.getId());
            } catch (MealDashUserAdapterException exception) {
                log.info("User not found in tearDown, skipping delete: {}", exception.getMessage());
            }
        }
    }


    @Test
    void testSaveUser_successful() throws MealDashUserAdapterException {
        MealDashUser savedMealDashUser = mealDashUserAdapter.save(mealDashUser);
        assertNotNull(savedMealDashUser);
        assertEquals("Joy", mealDashUser.getFirstName());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserAlreadyExist() throws MealDashUserAdapterException {
        mealDashUserAdapter.save(mealDashUser);

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserIsNull(){
        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(null));
        assertEquals(ErrorMessage.USER_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserFirstNameIsEmpty(){
        mealDashUser.setFirstName("");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.FIRST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserFirstNameIsNull(){
        mealDashUser.setFirstName(null);

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.FIRST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserFirstNameFormatIsInvalid(){
        mealDashUser.setFirstName("Mary123");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.INVALID_NAME_FORMAT, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserLastNameIsEmpty(){
        mealDashUser.setLastName("");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.LAST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserLastNameIsNull(){
        mealDashUser.setLastName(null);

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.LAST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserLastNameFormatIsInvalid(){
        mealDashUser.setLastName("Mary123");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.INVALID_NAME_FORMAT, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserEmailIsEmpty(){
        mealDashUser.setEmail("");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserEmailIsNull(){
        mealDashUser.setEmail(null);

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserEmailFormatIsInvalid(){
        mealDashUser.setEmail("~)(&we433");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.INVALID_MAIL_FORMAT, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsEmpty(){
        mealDashUser.setPassword("");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsNull(){
        mealDashUser.setPassword(null);

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_containsOnlySpecialCharacter(){
        mealDashUser.setPassword("@#$%^");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_containsOnlyNumbers(){
        mealDashUser.setPassword("12345");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_containsOnlyLowerCaseLetters(){
        mealDashUser.setPassword("abeyter");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_containsOnlyUpperCaseLetters(){
        mealDashUser.setPassword("ABYUNCE");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_lessThanEightCharacters(){
        mealDashUser.setPassword("Ab1!");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPhoneNumberIsEmpty(){
        mealDashUser.setPhoneNumber("");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPhoneNumberIsNull(){
        mealDashUser.setPhoneNumber(null);

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_REQUIRED, exception.getMessage());
    }


    @Test
    void testSaveUser_throwExceptionWhenPhoneNumberIsInvalid(){
        mealDashUser.setPhoneNumber("0908byu");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPhoneNumberIsInvalid_numberMoreThanEleven(){
        mealDashUser.setPhoneNumber("01234567890987");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(mealDashUser));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_INVALID, exception.getMessage());
    }

    @Test
    void testFindUserByEmail_successful() throws MealDashUserAdapterException {
        mealDashUserAdapter.save(mealDashUser);

        MealDashUser foundMealDashUser = mealDashUserAdapter.findByEmail(mealDashUser.getEmail());
        assertNotNull(foundMealDashUser);
        assertEquals(mealDashUser.getEmail(), foundMealDashUser.getEmail());
    }

    @Test
    void testFindUserByEmail_throwExceptionWhenUserNotFound(){
        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.findByEmail("hyu@gmail.com"));
        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testFindById_successful() throws MealDashUserAdapterException {
        mealDashUserAdapter.save(mealDashUser);

        MealDashUser foundMealDashUser = mealDashUserAdapter.findById(mealDashUser.getId());
        assertNotNull(foundMealDashUser);
        assertEquals(mealDashUser.getId(), foundMealDashUser.getId());
    }

    @Test
    void testFindUserById_throwExceptionWhenUserNotFound(){
        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.findByEmail("765@W45"));
        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testDeleteUserById_successful() throws MealDashUserAdapterException {
        mealDashUserAdapter.save(mealDashUser);
        mealDashUserAdapter.deleteById(mealDashUser.getId());

        assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.findById(mealDashUser.getId()));
    }


    @Test
    void testDeleteById_userNotFound_shouldThrowException() {
        String nonExistentId = "invalid123";

        Exception exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.deleteById(nonExistentId));

        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testFindAllUsers_successful() throws MealDashUserAdapterException {
        mealDashUserAdapter.save(mealDashUser);
        List<MealDashUser> findAll = mealDashUserAdapter.findAll();
        log.info("All users found {}", findAll);
        assertNotNull(findAll);
    }

    @Test
    void testFindAllUsers_usersNotFound_shouldThrowException() {
        Exception exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.findAll());

        assertEquals(ErrorMessage.NO_USERS_FOUND, exception.getMessage());
    }

}
