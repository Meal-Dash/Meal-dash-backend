package dash.meal.mealdash.domain.model;


import dash.meal.mealdash.domain.enums.VehicleType;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Vehicle {
    private String color;
    private String plateNumber;
    private String vehicleBrand;
    private String licensePlateNumber;
    private String licenseImage;
    private VehicleType vehicleType;
}
