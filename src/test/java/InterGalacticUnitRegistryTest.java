import com.uysalk.galactic.InterGalacticUnit;
import com.uysalk.galactic.InterGalacticUnitRegistry;
import com.uysalk.roman.RomanNumber;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;

/**
 * Created by uysal.kara on 11.01.2017.
 */
public class InterGalacticUnitRegistryTest {


    @Test
    public void testValidInterGalacticUnits (){
        InterGalacticUnitRegistry registry = new InterGalacticUnitRegistry();

        registry.register ( new InterGalacticUnit("glob", "I"));
        registry.register ( new InterGalacticUnit("prok", "V"));

        assertEquals (registry.getRomanNumber (Arrays.asList("glob prok".split(" "))), RomanNumber.builder().setRepr("IV").createRomanNumber());
        assertEquals (registry.getRomanNumber (" glob prok "), RomanNumber.builder().setRepr("IV").createRomanNumber());




    }

}
