import Utility.Rectangle2d;
import Utility.Vector2d;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Rectangle2dTests {

    @Test
    public void isInTest() {
        Rectangle2d box = new Rectangle2d(new Vector2d(0, 0), new Vector2d(5,5));

        assertTrue(box.isIn(new Vector2d(0 ,0)));
        assertTrue(box.isIn(new Vector2d(1 ,2)));
        assertTrue(box.isIn(new Vector2d(2 ,1)));
        assertTrue(box.isIn(new Vector2d(3 ,4)));
        assertTrue(box.isIn(new Vector2d(4 ,0)));

        assertFalse(box.isIn(new Vector2d(5 ,5)));
        assertFalse(box.isIn(new Vector2d(0 ,5)));
        assertFalse(box.isIn(new Vector2d(5 ,0)));
        assertFalse(box.isIn(new Vector2d(3 ,6)));
        assertFalse(box.isIn(new Vector2d(11 ,11)));
        assertFalse(box.isIn(new Vector2d(5 ,11)));
    }
}
