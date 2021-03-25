package lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ArrayAfterFourTest {
    private ArrayAfterDigitalFour digitalFour;

    @BeforeEach
    public void init() {
        digitalFour = new ArrayAfterDigitalFour();
    }

    @Test
    public void convertTest() {
        Assertions.assertArrayEquals(new int[]{5, 4}, digitalFour.convert(new int[]{1, 2, 4, 5, 6}));

    }
    @Test
    public void convertTest2() {

          Assertions.assertArrayEquals(new int[]{6}, digitalFour.convert(new int[]{1, 4, 4, 4, 6}));

    }
    @Test
    public void convertTest3() {

          Assertions.assertArrayEquals(new int[]{}, digitalFour.convert(new int[]{1, 2, 4, 5, 4}));

    }
    @Test
    public void convertTest4() {

          Assertions.assertArrayEquals(new int[]{5, 6, 9, 9}, digitalFour.convert(new int[]{1, 2, 4, 5, 6, 9, 9}));
    }

}