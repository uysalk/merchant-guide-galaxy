import com.uysalk.galactic.InterGalacticUnit;
import com.uysalk.galactic.InterGalacticUnitRegistry;
import com.uysalk.metal.Metal;
import com.uysalk.metal.MetalRegistry;
import com.uysalk.roman.RomanNumber;

import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by uysal.kara on 12.01.2017.
 */
public class MerchantProgram {

    private static final String rgxCredits = "((?:[a-z]+ )+)([A-Z]\\w+) is (\\d+) ([A-Z]\\w+)$";
    private static final String rgxHowMuch = "^how much is ((?:\\w+[^0-9] )+)\\?$";
    private static final String rgxHowMany = "^how many ([a-zA-Z]\\w+) is ((?:\\w+ )+)([A-Z]\\w+) \\?$";

    private static final  Pattern patternHowMuch = Pattern.compile(rgxHowMuch);
    private static final  Pattern patternHowMany = Pattern.compile(rgxHowMany);



    private final InterGalacticUnitRegistry interGalacticUnitRegistry;
    private final MetalRegistry metalRegistry;

    public MerchantProgram(InterGalacticUnitRegistry interGalacticUnitRegistry, MetalRegistry metalRegistry) {

        this.interGalacticUnitRegistry = interGalacticUnitRegistry;
        this.metalRegistry = metalRegistry;
    }

    public GalacticResponse interpret(String line) {


       if (line.endsWith("?")){
           return resolveQuestion (line);
       }else if (line.trim ().endsWith("Credits")){
            return resolveMetal(line);    //Glob Glob Silver is 34 Credits
       }else {
           return resolveGalacticUnit (line.split("is ")); //Glob  is  I
       }



    }

    private GalacticResponse resolveQuestion(String line) {
       final Matcher matcherHowMuch = patternHowMuch.matcher(line);

        if (  matcherHowMuch.matches()){
            return new GalacticResponse<String>() {

                @Override
                public String getResponse() {
                    RomanNumber romanNumber = interGalacticUnitRegistry.getRomanNumber(matcherHowMuch.group(1));
                    return matcherHowMuch.group(1) + "is " + romanNumber.toNumber() ;
                }
            };
        }

        // how many Credits is glob prok Gold ?
        final Matcher matcherHowMany = patternHowMany.matcher(line);
        if (  matcherHowMany.matches()){
            return new GalacticResponse<String>() {

                @Override
                public String getResponse() {
                    RomanNumber romanNumber = interGalacticUnitRegistry.getRomanNumber(matcherHowMany.group(2));
                    Metal metal = metalRegistry.get(matcherHowMany.group(3));
                    Double value = metal.value.get();
                    return matcherHowMany.group(2) +  matcherHowMany.group(3) + " is " + value*romanNumber.toNumber() +" " + matcherHowMany.group(1) ;
                }
            };
        }




        return new GalacticResponse<String>() {
            @Override
            public String getResponse() {
                return "I dont know what you are talking about";
            }
        };
    }


    private GalacticResponse resolveMetal(String line) {
        Pattern ptn = Pattern.compile(rgxCredits);
        Matcher mcher = ptn.matcher(line);
        if (mcher.matches()){
            Metal m = new Metal(mcher.group(2), new Supplier<Double>() {
                @Override
                public Double get() {
                    String galacticExpression = mcher.group(1);
                    RomanNumber romanNumber = interGalacticUnitRegistry.getRomanNumber(galacticExpression);
                    Integer value = Integer.parseInt(mcher.group(3));
                    return Double.valueOf(value /romanNumber.toNumber());
                }
            });
            metalRegistry.register(m);
            return new GalacticResponse() {
                @Override
                public Object getResponse() {
                    return null;
                }
            };
        }else return new GalacticResponse() {
            @Override
            public Object getResponse() {
                return null;
            }
        };

    }

    private GalacticResponse resolveGalacticUnit(String[] parts) {

        InterGalacticUnit interGalacticUnit = new InterGalacticUnit(parts[0].trim(), parts[1].trim());
        interGalacticUnitRegistry.register(interGalacticUnit);
        return new GalacticResponse() {
            @Override
            public Object getResponse() {
                return null;
            }
        };
    }
}
