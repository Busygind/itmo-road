package org.example.task3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Transmission {
    private TransmissionGear currentGear = TransmissionGear.PARKING;

    public boolean setCurrentGear(TransmissionGear gear) {
        if (currentGear == gear) {
            System.out.println("Ничего не поменялось, всё еще " + gear.getName() + " передача");
            return true;
        }
        if (changeIsValid(currentGear, gear)) {
            System.out.println("Сменили передачу! Была " + currentGear + ", стала " + gear + "!");
            currentGear = gear;
            return true;
        } else {
            System.out.println("О нет... Кажется при попытке сменить передачу что-то пошло не так");
            return false;
        }
    }

    public boolean changeIsValid(TransmissionGear initialGear, TransmissionGear desiredGear) {
        return initialGear == desiredGear || switch (initialGear) {
            case PARKING ->
                    List.of(
                            TransmissionGear.REVERSE,
                            TransmissionGear.NEUTRAL,
                            TransmissionGear.FIRST
                    ).contains(desiredGear);
            case FIRST ->
                    List.of(
                            TransmissionGear.REVERSE,
                            TransmissionGear.NEUTRAL,
                            TransmissionGear.SECOND,
                            TransmissionGear.PARKING
                    ).contains(desiredGear);
            case SECOND ->
                    List.of(
                            TransmissionGear.FIRST,
                            TransmissionGear.NEUTRAL,
                            TransmissionGear.THIRD
                    ).contains(desiredGear);
            case THIRD ->
                    List.of(
                            TransmissionGear.SECOND,
                            TransmissionGear.NEUTRAL,
                            TransmissionGear.FOURTH
                    ).contains(desiredGear);
            case FOURTH ->
                    List.of(
                            TransmissionGear.THIRD,
                            TransmissionGear.NEUTRAL,
                            TransmissionGear.FIFTH
                    ).contains(desiredGear);
            case FIFTH ->
                    List.of(
                            TransmissionGear.FOURTH,
                            TransmissionGear.NEUTRAL
                    ).contains(desiredGear);
            case NEUTRAL ->
                    List.of(
                            TransmissionGear.REVERSE,
                            TransmissionGear.PARKING,
                            TransmissionGear.FIRST,
                            TransmissionGear.SECOND,
                            TransmissionGear.THIRD,
                            TransmissionGear.FOURTH,
                            TransmissionGear.FIFTH
                    ).contains(desiredGear);
            case REVERSE ->
                    List.of(
                            TransmissionGear.NEUTRAL,
                            TransmissionGear.PARKING
                    ).contains(desiredGear);
        };
    }

}
