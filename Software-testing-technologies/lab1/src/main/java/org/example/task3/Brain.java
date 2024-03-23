package org.example.task3;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Brain implements AbleToFlyOut {

    private boolean flewOut;

    @Override
    public void flyOut() {
        flewOut = true;
    }
}
