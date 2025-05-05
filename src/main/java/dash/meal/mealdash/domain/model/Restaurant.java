package dash.meal.mealdash.domain.model;

import dash.meal.mealdash.infrastructure.adapter.output.persistence.entity.LocationEntity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class Restaurant {
    private String id;
    private List<MealDashUser> mealDashUser;
    private String name;
    private String password;
    private Location currentLocation;
}
