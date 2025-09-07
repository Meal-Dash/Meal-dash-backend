package dash.meal.mealdash.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Location {
    private String address;
    private String city;
    private String state;
    private String postalCode;
}
