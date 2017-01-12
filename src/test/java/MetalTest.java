import com.uysalk.metal.Metal;
import com.uysalk.metal.MetalRegistry;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

/**
 * Created by uysal.kara on 11.01.2017.
 */
public class MetalTest {


    @Test
    public void testValidMetal () throws ExecutionException, InterruptedException {

        MetalRegistry registry = new MetalRegistry();

        Metal m =     new Metal ("Silver", ()-> 14d);


        registry.register (m);

        Metal silver = registry.get ("Silver");

        assertEquals (m, silver);

        Metal gold = registry.get ("Gold");

        assertNull (gold);
    }

}
