package org.example.task3;

import lombok.Getter;

@Getter
public enum TransmissionGear {
    FIRST("Первая", 0.005),
    SECOND("Вторая", 0.01),
    THIRD("Третья", 0.015),
    FOURTH("Четвертая", 0.02),
    FIFTH("Пятая", 0.03),
    REVERSE("Реверсивная", -0.003),
    NEUTRAL("Нейтральная", 0),
    PARKING("Парковка", 0);

    private final String name;
    private final double ratio;

    TransmissionGear(String name, double ratio) {
        this.name = name;
        this.ratio = ratio;
    }
}
