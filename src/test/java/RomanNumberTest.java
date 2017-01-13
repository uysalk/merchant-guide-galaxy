import com.uysalk.roman.RomanNumber;
import org.junit.Test;

import java.util.Optional;

import static junit.framework.Assert.assertEquals;

/**
 * Created by uysal.kara on 11.01.2017.
 */
public class RomanNumberTest {


    @Test
    public void testValidRomanNumber (){

        assertEquals(RomanNumber.builder().setRepr("I").createRomanNumber().get().toNumber (), 1);
        assertEquals(RomanNumber.builder().setRepr("II").createRomanNumber().get().toNumber (), 2);
        assertEquals(RomanNumber.builder().setRepr("III").createRomanNumber().get().toNumber (), 3);
        assertEquals(RomanNumber.builder().setRepr("MMIX").createRomanNumber().get().toNumber (), 2009);

    }

    @Test
    public void testInValidRomanNumber (){

        assertEquals(RomanNumber.builder().setRepr("IIII").createRomanNumber(), Optional.empty());
        assertEquals(RomanNumber.builder().setRepr("IIIIV").createRomanNumber(), Optional.empty());


    }

}
