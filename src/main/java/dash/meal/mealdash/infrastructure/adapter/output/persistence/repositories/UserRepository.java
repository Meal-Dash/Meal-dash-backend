package dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories;

import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.MealDashEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MealDashEntity, String> {
    Optional<MealDashEntity> findByEmail(String email);


}
