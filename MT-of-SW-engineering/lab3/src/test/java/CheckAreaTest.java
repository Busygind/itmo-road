import com.busygind.lab3.entities.Hit;
import com.busygind.lab3.utilities.AreaChecker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(Parameterized.class)
public class CheckAreaTest {
    private static final AreaChecker checker = new AreaChecker();
    private double x;
    private double y;
    private double r;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] { {1.0}, {1.5}, {2.0}, {2.5}, {3.0} });
    }

    public CheckAreaTest(Double r) {
        this.r = r;
    }

    @Test
    public void centerIn() {
        x = 0;
        y = 0;
        assertTrue(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void centerOut() {
        x = 0 + 0.0000001f;
        y = 0 + 0.0000001f;
        assertFalse(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void triangleDownCornerIn() {
        x = 0;
        y = -r;
        assertTrue(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void triangleDownCornerOut() {
        x = 0;
        y = -r - 0.000001f;
        assertFalse(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void triangleLeftCornerIn() {
        x = -r;
        y = 0;
        assertTrue(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void triangleLeftCornerOut() {
        x = -r - 0.000001f;
        y = 0;
        assertFalse(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void rectangleLeftUpperCornerIn() {
        x = -r;
        y = r;
        assertTrue(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void rectangleLeftUpperCornerOut() {
        x = -r - 0.000001f;
        y = r;
        assertFalse(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void rectangleRightUpperCornerIn() {
        x = 0;
        y = r;
        assertTrue(checker.checkHit(new Hit(x, y, r)));
    }

    @Test
    public void rectangleRightUpperCornerOut() {
        x = 0;
        y = r + 0.000001f;
        assertFalse(checker.checkHit(new Hit(x, y, r)));
    }
}
