package dash.meal.mealdash.application.output;

import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserOutputPort {
    User save(User user) throws MealDashUserAdapterException;
    User findById(String id) throws MealDashUserAdapterException;
    User findByEmail(String email) throws MealDashUserAdapterException;
    List<User> findAll() throws MealDashUserAdapterException;
    void deleteById(String id) throws MealDashUserAdapterException;
    void deleteAll() throws MealDashUserAdapterException;

}
