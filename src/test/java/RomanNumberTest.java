import com.uysalk.roman.RomanNumber;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Created by uysal.kara on 11.01.2017.
 */
public class RomanNumberTest {


    @Test
    public void testValidRomanNumber (){
        RomanNumber.builder().setRepr("I").createRomanNumber();

        assertEquals(RomanNumber.builder().setRepr("I").createRomanNumber().toNumber (), 1);
        assertEquals(RomanNumber.builder().setRepr("II").createRomanNumber().toNumber (), 2);
        assertEquals(RomanNumber.builder().setRepr("III").createRomanNumber().toNumber (), 3);
        assertEquals(RomanNumber.builder().setRepr("MMIX").createRomanNumber().toNumber (), 2009);

    }

}
