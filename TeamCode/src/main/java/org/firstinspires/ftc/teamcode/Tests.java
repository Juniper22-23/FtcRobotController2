package org.firstinspires.ftc.teamcode;

import org.junit.Test;
import static org.junit.Assert.*;


public class Tests {

    @Test
     public void JunitMethod() {
         Add calculator = new Add();
         assertEquals(4, calculator.add(2,2));
    }
    @Test
    public void sixplussix(){
        Add calculator = new Add();
        assertEquals(12, calculator.add(6,6));

    }

}
