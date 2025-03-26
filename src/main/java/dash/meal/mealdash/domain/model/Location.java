package dash.meal.mealdash.domain.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Location {
    private String address;
    private String city;
    private String state;
    private String postalCode;
}
