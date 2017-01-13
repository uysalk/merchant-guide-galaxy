import com.uysalk.GalacticResponse;
import com.uysalk.MerchantProgram;
import com.uysalk.galactic.InterGalacticUnitRegistry;
import com.uysalk.metal.MetalRegistry;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by uysal.kara on 11.01.2017.
 */
public class MerchantProgramTest {


    @Test
    public void testMerchantProgram (){


        final InterGalacticUnitRegistry interGalacticUnitRegistry = new InterGalacticUnitRegistry();
        final MetalRegistry metalRegistry = new MetalRegistry();

        MerchantProgram merchant = new MerchantProgram(interGalacticUnitRegistry, metalRegistry);
        merchant.interpret ("glob is I");
        merchant.interpret ("prok is V");
        merchant.interpret ("pish is X");
        merchant.interpret ("tegj is L");

        merchant.interpret ("glob prok Silver is 36 Credits");

        assertNotNull (interGalacticUnitRegistry.getGalacticUnit ("glob"));
        assertEquals ("X",interGalacticUnitRegistry.getGalacticUnit ("pish").romanRepresentation);

        assertNotNull (metalRegistry.get("Silver") );

        GalacticResponse response = merchant.interpret("how much is pish tegj glob glob ?");
        assertEquals ( "pish tegj glob glob is 42" , response.getResponse());

        GalacticResponse responseHowmany = merchant.interpret("how many Credits is glob prok Silver ?");
        assertEquals ( "glob prok Silver is 36.0 Credits" , responseHowmany.getResponse());

        assertEquals (9d, metalRegistry.get("Silver").value.get());

        GalacticResponse invalidResponse = merchant.interpret("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?");
        assertEquals("I have no idea what you are talking about", invalidResponse.getResponse());

    }

}
