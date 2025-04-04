package dash.meal.mealdash.application.output;

import dash.meal.mealdash.domain.exception.MealDashException;
import dash.meal.mealdash.domain.exception.UserAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;

import java.util.List;

public interface UserOutputPort {
    MealDashUser save(MealDashUser mealDashUser) throws UserAdapterException, MealDashException;
    MealDashUser findById(String id) throws UserAdapterException;
    MealDashUser findByEmail(String email) throws UserAdapterException;
    List<MealDashUser> findAll() throws UserAdapterException;
    void deleteById(String id) throws UserAdapterException;
    boolean existsByEmail(String email);
    void deleteAll() throws UserAdapterException;

}
