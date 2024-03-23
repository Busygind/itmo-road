package task2;

import org.example.task2.OpenHash;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OpenHashTest {

    private OpenHash hashTable = new OpenHash(10);

    @BeforeEach
    public void init() {
        hashTable = new OpenHash(10);
    }

    @Test
    public void testDuplicationValues() {
        int digit = 10;
        hashTable.insert(digit);
        assertTrue(hashTable.isInside(digit));
        hashTable.insert(digit);
        assertTrue(hashTable.isInside(digit));
        hashTable.delete(digit);
        assertTrue(hashTable.isInside(digit));
        hashTable.delete(digit);
        assertFalse(hashTable.isInside(digit));
    }

    @Test
    public void fillAndClear() {
        for (int i = 0; i < 50; i++) {
            hashTable.insert(i);
            assertTrue(hashTable.isInside(i));
        }
        for (int i = 0; i < 50; i++) {
            hashTable.delete(i);
            assertFalse(hashTable.isInside(i));
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    public void initHashTableWithIncorrectCapacity(Integer capacity) {
        assertThrows(
                IllegalArgumentException.class,
                () -> hashTable = new OpenHash(capacity)
        );
    }
}
