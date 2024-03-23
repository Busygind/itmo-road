package task3;

import org.example.task3.Brain;
import org.example.task3.Car;
import org.example.task3.Engine;
import org.example.task3.Person;
import org.example.task3.Transmission;
import org.example.task3.TransmissionGear;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StoryTest {

    Transmission transmission;
    Engine engine;
    Car car;
    Brain brain;
    Person person;

    @BeforeEach
    public void init() {
        transmission = new Transmission();
        engine = new Engine();
        car = new Car(transmission, engine);
        brain = new Brain();
        person = new Person(brain, "Вы", car);
    }

    @Test
    public void startWhenAlreadyOnTheRoad() {
        car.setCurrentSpeed(100);
        car.setOnTheRoad(true);

        assertFalse(person.startDriving());
        assertTrue(car.isOnTheRoad());
        assertNotEquals(0, car.getCurrentSpeed());
    }

    @Test
    public void startSuccess() {
        car.setOnTheRoad(false);
        car.setHasBrokenDetails(false);

        assertTrue(person.startDriving());

        assertTrue(car.isOnTheRoad());
        assertNotEquals(0, car.getCurrentSpeed());
    }

    @Test
    public void startWithBrokenDetails() {
        car.setOnTheRoad(false);
        car.setHasBrokenDetails(true);

        assertFalse(person.startDriving());

        assertFalse(car.isOnTheRoad());
        assertEquals(0, car.getCurrentSpeed());
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "FIRST:SECOND",
                    "FIRST:FIRST",
                    "PARKING:FIRST",
                    "FIFTH:FOURTH",
                    "NEUTRAL:FIFTH",
                    "PARKING:NEUTRAL",
                    "FOURTH:THIRD",
                    "PARKING:NEUTRAL",
                    "NEUTRAL:REVERSE",
            }, delimiter = ':')
    @DisplayName("perform possible gears changes")
    public void performPossibleGearsChanges(String initial, String desired) {
        assertTrue(transmission.changeIsValid(TransmissionGear.valueOf(initial), TransmissionGear.valueOf(desired)));
    }

    @ParameterizedTest
    @MethodSource("provideIncorrectGearsPairs")
    @DisplayName("perform impossible gears changes")
    public void performImpossibleGearsChanges(TransmissionGear initial, TransmissionGear desired) {
        assertFalse(transmission.changeIsValid(initial, desired));
    }

    @Test
    public void checkFlyingOut() {
        person.startDriving();
        assertFalse(person.changeGear(TransmissionGear.FIFTH));

        assertTrue(brain.isFlewOut());
        assertTrue(engine.isFlewOut());
    }

    private static Stream<Arguments> provideIncorrectGearsPairs() {
        return Stream.of(
                Arguments.of(TransmissionGear.FIRST, TransmissionGear.FIFTH),
                Arguments.of(TransmissionGear.FIRST, TransmissionGear.FOURTH),
                Arguments.of(TransmissionGear.PARKING, TransmissionGear.SECOND),
                Arguments.of(TransmissionGear.PARKING, TransmissionGear.FIFTH),
                Arguments.of(TransmissionGear.FOURTH, TransmissionGear.REVERSE),
                Arguments.of(TransmissionGear.SECOND, TransmissionGear.REVERSE)
        );
    }
}
