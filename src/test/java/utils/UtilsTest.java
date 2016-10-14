package utils;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by user on 11/01/16.
 */
public class UtilsTest {

    @Test
    public void testIsInteger(){
        String strInt = "5";
        assertTrue(Utils.isInteger(strInt));
        String strNotInt = "6slqdqsld";
        assertFalse(Utils.isInteger(strNotInt));
    }

}
