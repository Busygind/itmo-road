package com.busygind.lab3.utilities;

import com.busygind.lab3.entities.Hit;

public class AreaChecker {
    public boolean checkHit(Hit hit) {
        double x = hit.getX();
        double y = hit.getY();
        double r = hit.getR();
        return (x <= 0 && y <= 0 && x + y >= -r) ||
                (x >= 0 && y <= 0 && x * x + y * y <= r * r / 4) ||
                (x <= 0 && y >= 0 && x >= -r && y <= r);
    }
}
