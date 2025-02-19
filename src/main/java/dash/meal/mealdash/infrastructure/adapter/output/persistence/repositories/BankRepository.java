package dash.meal.mealdash.infrastructure.adapter.output.persistence.repositories;

import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository extends JpaRepository<BankEntity, String> {
}
