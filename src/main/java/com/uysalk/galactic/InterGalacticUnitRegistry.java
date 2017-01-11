package com.uysalk.galactic;

import com.uysalk.roman.RomanNumber;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by uysal.kara on 11.01.2017.
 */
public class InterGalacticUnitRegistry {
    Map<String, InterGalacticUnit> registry = new HashMap<String, InterGalacticUnit>();

    public void register(InterGalacticUnit interGalacticUnit) {
        registry.put(interGalacticUnit.galacticRepresentation, interGalacticUnit);
    }


    public RomanNumber getRomanNumber(List<String> split) {
      String romanNumber =   split.stream().reduce("", (String x, String y) -> { return x + registry.get(y).romanRepresentation;} );
      return RomanNumber.builder().setRepr(romanNumber).createRomanNumber();
    }

    public RomanNumber getRomanNumber(String line) {
        List<String> collect = Arrays.stream(line.split(" ")).filter((x)-> ! x.isEmpty()).collect(Collectors.toList());
        return getRomanNumber (collect);
    }
}
