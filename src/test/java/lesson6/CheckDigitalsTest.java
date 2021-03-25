package lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckDigitalsTest {
    private CheckDigitals checkDigitals;

    @BeforeEach
    public void init() {
        checkDigitals = new CheckDigitals();
    }
        @Test
        public void checkTest(){
            Assertions.assertEquals(true,checkDigitals.check(new int[]{4, 2, 4,5}));
        }
    @Test
    public void checkTest1(){
        Assertions.assertEquals(true,checkDigitals.check(new int[]{1, 2, 4,5}));
    }
    @Test
    public void checkTest2(){
        Assertions.assertEquals(true,checkDigitals.check(new int[]{4, 2, 4,1}));
    }
    @Test
    public void checkTest3(){
        Assertions.assertEquals(true,checkDigitals.check(new int[]{1, 2, 2,5}));
    }
    }
