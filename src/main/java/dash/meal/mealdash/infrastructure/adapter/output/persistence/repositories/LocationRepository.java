package dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories;

import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationEntity, String> {
}
