package dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories;

import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.MealDashEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MealDashEntity, String> {
    Optional<MealDashEntity> findByEmail(String email);
    boolean existsByEmail(String email);


}
