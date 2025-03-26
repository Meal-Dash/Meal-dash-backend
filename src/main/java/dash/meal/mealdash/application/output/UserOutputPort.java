package dash.meal.mealdash.application.output;

import dash.meal.mealdash.domain.exception.MealDashUserAdapterException;
import dash.meal.mealdash.domain.model.MealDashUser;

import java.util.List;

public interface UserOutputPort {
    MealDashUser save(MealDashUser mealDashUser) throws MealDashUserAdapterException;
    MealDashUser findById(String id) throws MealDashUserAdapterException;
    MealDashUser findByEmail(String email) throws MealDashUserAdapterException;
    List<MealDashUser> findAll() throws MealDashUserAdapterException;
    void deleteById(String id) throws MealDashUserAdapterException;
    boolean existsByEmail(String email);
    void deleteAll() throws MealDashUserAdapterException;

}
