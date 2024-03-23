package org.example.task3;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Engine implements AbleToFlyOut {

    private boolean isWorking;
    private int rpm;
    private boolean flewOut;

    public Engine() {
        isWorking = true;
        rpm = 1000;
        flewOut = false;
    }

    @Override
    public void flyOut() {
        flewOut = true;
    }
}
