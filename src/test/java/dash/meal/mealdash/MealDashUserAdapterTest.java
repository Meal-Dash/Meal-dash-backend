package dash.meal.mealdash;

import dash.meal.mealdash.domain.exception.ErrorMessage;
import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.model.User;
import dash.meal.mealdash.infrastructure.adapter.output.persistence.user.MealDashUserAdapter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MealDashUserAdapterTest {

    @Autowired
    private MealDashUserAdapter mealDashUserAdapter;

    @AfterEach
    void tearDown() {
        mealDashUserAdapter.deleteAll();
    }


    @Test
    void testSaveUser_successful() throws MealDashUserAdapterException {
        User user = new User();
        user.setId("John123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("joy123@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("09018296447");

        User savedUser = mealDashUserAdapter.save(user);
        assertNotNull(savedUser);
    }

    @Test
    void testSaveUser_throwExceptionWhenUserAlreadyExist() throws MealDashUserAdapterException {
        User user = new User();
        user.setId("John123");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("joy123@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("09018296447");

        mealDashUserAdapter.save(user);

        User user2 = new User();
        user2.setId("John123");
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setEmail("joy123@gmail.com");
        user2.setPassword("Password1@");
        user2.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user2));
        assertEquals(ErrorMessage.USER_ALREADY_EXIST, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserIsNull(){
        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(null));
        assertEquals(ErrorMessage.USER_CANNOT_BE_NULL, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserFirstNameIsEmpty(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("");
        user.setLastName("Doe");
        user.setEmail("joy123@gmail.com");
        user.setPassword("A1b2C3d!");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.FIRST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserFirstNameIsNull(){
        User user = new User();
        user.setId("John123");
        user.setFirstName(null);
        user.setLastName("Doe");
        user.setEmail("joy123@gmail.com");
        user.setPassword("AbcD123$");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.FIRST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserFirstNameFormatIsInvalid(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("Mary123");
        user.setLastName("Doe");
        user.setEmail("joy123@gmail.com");
        user.setPassword("AbcD123$");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.INVALID_NAME_FORMAT, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserLastNameIsEmpty(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("Doe");
        user.setLastName("");
        user.setEmail("joy123@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.LAST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserLastNameIsNull(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("John");
        user.setLastName(null);
        user.setEmail("joy123@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.LAST_NAME_MUST_BE_PROVIDED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserLastNameFormatIsInvalid(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("Mary");
        user.setLastName("Mary123");
        user.setEmail("joy123@gmail.com");
        user.setPassword("AbcD123$");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.INVALID_NAME_FORMAT, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserEmailIsEmpty(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setEmail("");
        user.setPassword("A1b2C3d!");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserEmailIsNull(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail(null);
        user.setPassword("Password1@");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.EMAIL_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenUserEmailFormatIsInvalid(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("john");
        user.setLastName("Doe");
        user.setEmail("~)(&we433");
        user.setPassword("AbcD123$");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.INVALID_MAIL_FORMAT, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsEmpty(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PASSWORD_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsNull(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword(null);
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PASSWORD_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_containsOnlySpecialCharacter(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("@#$%^");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_containsOnlyNumbers(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("12345");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_containsOnlyLowerCaseLetters(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("abeyter");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_containsOnlyUpperCaseLetters(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("ABYUNCE");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPasswordIsValid_lessThanEightCharacters(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Ab1!");
        user.setPhoneNumber("09018296447");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PASSWORD_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPhoneNumberIsEmpty(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_REQUIRED, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPhoneNumberIsNull(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber(null);

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_REQUIRED, exception.getMessage());
    }


    @Test
    void testSaveUser_throwExceptionWhenPhoneNumberIsInvalid(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("0908byu");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_INVALID, exception.getMessage());
    }

    @Test
    void testSaveUser_throwExceptionWhenPhoneNumberIsInvalid_numberMoreThanEleven(){
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("01234567890987");

        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.save(user));
        assertEquals(ErrorMessage.PHONE_NUMBER_IS_INVALID, exception.getMessage());
    }

    @Test
    void testFindUserByEmail_successful() throws MealDashUserAdapterException {
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("01234567876");
        mealDashUserAdapter.save(user);

        User foundUser = mealDashUserAdapter.findByEmail(user.getEmail());
        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
    }

    @Test
    void testFindUserByEmail_throwExceptionWhenUserNotFound(){
        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.findByEmail("hyu@gmail.com"));
        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testFindById_successful() throws MealDashUserAdapterException {
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("01234567876");
        mealDashUserAdapter.save(user);

        User foundUser = mealDashUserAdapter.findById(user.getId());
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
    }

    @Test
    void testFindUserById_throwExceptionWhenUserNotFound(){
        MealDashUserAdapterException exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.findByEmail("765@W45"));
        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testDeleteUserById_successful() throws MealDashUserAdapterException {
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("01234567876");

        mealDashUserAdapter.save(user);
        mealDashUserAdapter.deleteById(user.getId());

        assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.findById(user.getId()));
    }


    @Test
    void testDeleteById_userNotFound_shouldThrowException() {
        String nonExistentId = "invalid123";

        Exception exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.deleteById(nonExistentId));

        assertEquals(ErrorMessage.USER_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testFindAllUsers_successful() throws MealDashUserAdapterException {
        User user = new User();
        user.setId("John123");
        user.setFirstName("joy");
        user.setLastName("Doe");
        user.setEmail("joy@gmail.com");
        user.setPassword("Password1@");
        user.setPhoneNumber("01234567876");
        mealDashUserAdapter.save(user);

        User user2 = new User();
        user2.setId("John1234");
        user2.setFirstName("joys");
        user2.setLastName("Does");
        user2.setEmail("joy12@gmail.com");
        user2.setPassword("PaSsword1@");
        user2.setPhoneNumber("01234567676");
        mealDashUserAdapter.save(user2);

        List<User> findAll = mealDashUserAdapter.findAll();
        assertNotNull(findAll);
    }

    @Test
    void testFindAllUsers_usersNotFound_shouldThrowException() {
        Exception exception = assertThrows(MealDashUserAdapterException.class, () -> mealDashUserAdapter.findAll());

        assertEquals(ErrorMessage.NO_USERS_FOUND, exception.getMessage());
    }

}
