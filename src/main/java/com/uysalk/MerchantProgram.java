package com.uysalk;

import com.uysalk.galactic.InterGalacticUnit;
import com.uysalk.galactic.InterGalacticUnitRegistry;
import com.uysalk.metal.Metal;
import com.uysalk.metal.MetalRegistry;
import com.uysalk.roman.RomanNumber;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by uysal.kara on 12.01.2017.
 */
public class MerchantProgram {

    private static final String rgxGalacticUnit = "([a-z]+) is ([I|V|X|L|C|D|M])$";
    private static final String rgxHowMuch = "^how much is ((?:\\w+[^0-9] )+)\\?$";
    private static final String rgxHowMany = "^how many ([a-zA-Z]\\w+) is ((?:\\w+ )+)([A-Z]\\w+) \\?$";
    private static final String rgxCredits = "((?:[a-z]+ )+)([A-Z]\\w+) is (\\d+) ([A-Z]\\w+)$";

    private static final  Pattern patternHowMuch = Pattern.compile(rgxHowMuch);
    private static final  Pattern patternHowMany = Pattern.compile(rgxHowMany);
    private static final  Pattern patternCredits = Pattern.compile(rgxCredits);
    private static final  Pattern patternGalacticUnit = Pattern.compile(rgxGalacticUnit);



    private final InterGalacticUnitRegistry interGalacticUnitRegistry;
    private final MetalRegistry metalRegistry;

    public MerchantProgram(InterGalacticUnitRegistry interGalacticUnitRegistry, MetalRegistry metalRegistry) {

        this.interGalacticUnitRegistry = interGalacticUnitRegistry;
        this.metalRegistry = metalRegistry;
    }

    public GalacticResponse interpret(String line) {
        final Matcher matcherHowMuch = patternHowMuch.matcher(line);
        if (  matcherHowMuch.matches()) {
            return  resolveHowMuchQuestion (matcherHowMuch, interGalacticUnitRegistry);
        }
        final Matcher matcherHowMany = patternHowMany.matcher(line);
        if (  matcherHowMany.matches()) {
            return  resolveHowManyQuestion (matcherHowMany, interGalacticUnitRegistry);
        }

        final Matcher matcherCredits = patternCredits.matcher(line);
        if (  matcherCredits.matches()) {
            return resolveMetal(matcherCredits, interGalacticUnitRegistry);  //Glob Glob Silver is 34 Credits
        }

        final Matcher matcherGalacticUnit = patternGalacticUnit.matcher(line);
        if (  matcherGalacticUnit.matches()) {
            return resolveGalacticUnit(matcherGalacticUnit, interGalacticUnitRegistry);  //Glob Glob Silver is 34 Credits
        }
        return new GalacticResponse<String>() {
            @Override
            public String getResponse() {
                return "I have no idea what you are talking about";
            }
        };

    }

    private GalacticResponse resolveHowMuchQuestion(Matcher matcherHowMuch, InterGalacticUnitRegistry interGalacticUnitRegistry) {
        return new GalacticResponse<String>() {

            @Override
            public String getResponse() {

                Optional<RomanNumber> romanNumber = interGalacticUnitRegistry.getRomanNumber(matcherHowMuch.group(1));
                if (romanNumber.isPresent()){

                    return matcherHowMuch.group(1) + "is " + romanNumber.get().toNumber() ;
                }else{
                    return "I have no idea what you are talking about";

                }
            }
        };
    }

    private GalacticResponse resolveHowManyQuestion( Matcher matcherHowMany, InterGalacticUnitRegistry interGalacticUnitRegistry) {
        // how many Credits is glob prok Gold ?
        return new GalacticResponse<String>() {

                @Override
                public String getResponse() {
                    Optional<RomanNumber> romanNumber = interGalacticUnitRegistry.getRomanNumber(matcherHowMany.group(2));
                    if (romanNumber.isPresent()){
                        Metal metal = metalRegistry.get(matcherHowMany.group(3));
                        Double value = metal.value.get();
                        return matcherHowMany.group(2) +  matcherHowMany.group(3) + " is " + value * romanNumber.get().toNumber() +" " + matcherHowMany.group(1) ;
                    }else{
                        return "I have no idea what you are talking about";
                    }
                }
            };

    }


    private GalacticResponse resolveMetal(Matcher matcher, InterGalacticUnitRegistry interGalacticUnitRegistry) {
        Metal m = new Metal(matcher.group(2), new Supplier<Double>() {
            @Override
            public Double get() {
                String galacticExpression = matcher.group(1);
                Optional<RomanNumber> romanNumber = interGalacticUnitRegistry.getRomanNumber(galacticExpression);
                if (romanNumber.isPresent()){
                    Integer value = Integer.parseInt(matcher.group(3));
                    return Double.valueOf(value /romanNumber.get().toNumber());
                }else{
                    return 0d;
                }
            }
        });
        metalRegistry.register(m);
        return new GalacticResponse<String>() {
            @Override
            public String getResponse() {
                return "";
            }
        };
    }

    private GalacticResponse resolveGalacticUnit(Matcher matcher, InterGalacticUnitRegistry interGalacticUnitRegistry) {

        InterGalacticUnit interGalacticUnit = new InterGalacticUnit(matcher.group(1), matcher.group(2));
        interGalacticUnitRegistry.register(interGalacticUnit);
        return new GalacticResponse<String>() {
            @Override
            public String getResponse() {
                return "";
            }
        };
    }
}
