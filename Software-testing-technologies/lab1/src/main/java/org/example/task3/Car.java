package org.example.task3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private Transmission transmission;
    private Engine engine;
    private boolean onTheRoad;
    private boolean hasBrokenDetails;
    private double currentSpeed;

    public Car(Transmission transmission, Engine engine) {
        this.transmission = transmission;
        this.engine = engine;
        this.hasBrokenDetails = false;
        this.currentSpeed = 0;
        this.onTheRoad = false;
    }

    public boolean tryStart() {
        if (onTheRoad) {
            System.out.println("А мы уже и так едем!");
            return false;
        }
        if (hasBrokenDetails) {
            System.out.println("Без механика не поедем");
            return false;
        }
        if (transmission.setCurrentGear(TransmissionGear.FIRST)) {
            engine.setRpm(3000);
            onTheRoad = true;
            setSpeed(engine.getRpm() * transmission.getCurrentGear().getRatio());
            return true;
        }
        System.out.println("Кажется, что-то сломалось при попытке поехать");
        hasBrokenDetails = true;
        onTheRoad = false;
        return false;
    }

    public boolean changeGear(TransmissionGear gear) {
        if (transmission.setCurrentGear(gear)) {
            setSpeed(engine.getRpm() * transmission.getCurrentGear().getRatio());
            return true;
        } else {
            System.out.println("Двигатель покидает машину, до связи");
            engine.flyOut();
            hasBrokenDetails = true;
            onTheRoad = false;
            return false;
        }
    }

    public boolean hasBrokenDetails() {
        return hasBrokenDetails;
    }

    public void setSpeed(double speed) {
        currentSpeed = speed;
        System.out.println("Едем со скоростью " + speed);
    }
}
