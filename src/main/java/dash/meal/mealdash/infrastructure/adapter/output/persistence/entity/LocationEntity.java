package dash.meal.mealdash.infrastructure.adapter.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationEntity {
    @Id
    private String id;
    private String address;
    private String city;
    private String state;
    private String postalCode;
}
