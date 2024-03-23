package org.example.task3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private Brain brain;
    private String name;
    private Car car;

    public boolean startDriving() {
        if (car == null) {
            System.out.println("Сначала купи тачку");
            return false;
        }
        if (car.tryStart()) {
            System.out.println("Поехали!");
            return true;
        } else {
            System.out.println("Это корыто не поедет, надо на метро");
            return false;
        }
    }

    public boolean changeGear(TransmissionGear gear) {
        if (car != null && !car.hasBrokenDetails()) {
            if (!car.changeGear(gear)) {
                System.out.println("Мозг покидает машину, до связи");
                brain.flyOut();
                return false;
            }
            return true;
        } else {
            System.out.println("Тачки нет, ничего не поделать");
            return false;
        }
    }
}
