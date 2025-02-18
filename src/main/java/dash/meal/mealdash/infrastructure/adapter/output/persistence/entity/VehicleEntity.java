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
public class VehicleEntity {
    @Id
    private String id;
    private String color;
    private String plateNumber;
    private String vehicleBrand;
    private String licensePlateNumber;
    private String licenseImage;
    private VehicleType vehicleType;
}
