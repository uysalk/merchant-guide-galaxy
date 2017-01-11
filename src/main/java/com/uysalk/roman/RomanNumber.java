package com.uysalk.roman;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by uysal.kara on 11.01.2017.
 */
public class RomanNumber {

    private String representation;

    private RomanNumber(String repr) {
        representation = repr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RomanNumber that = (RomanNumber) o;

        return representation != null ? representation.equals(that.representation) : that.representation == null;
    }

    @Override
    public int hashCode() {
        return representation != null ? representation.hashCode() : 0;
    }

    public int toNumber() {
        StringBuilder builder = new StringBuilder(representation);
        builder.reverse();
        int value = 0, previous = 0;

        for (int i = 0; i < builder.length(); i++) {
            try {
                int current = literals.get(String.valueOf(builder.charAt(i)));
                // If the value suddenly drops then subtract, don't add.
                value += current < previous ? -current : current;
                previous = current;
            } catch (NullPointerException e) {
                // A NullPointerException will be thrown only if the
                // character is not present in the map.
                throw new NumberFormatException
                        ("Unrecognised character : " + builder.charAt(i));
            }
        }
        return value;
    }




    public static final Map<String, Integer> literals;

    static {
        Map<String, Integer> temporary = new LinkedHashMap<>(13);
        temporary.put("M", 1000);
        //temporary.put("CM", 900);
        temporary.put("D", 500);
        //temporary.put("CD", 400);
        temporary.put("C", 100);
        //temporary.put("XC", 90);
        temporary.put("L", 50);
        //temporary.put("XL", 40);
        temporary.put("X", 10);
        //temporary.put("IX", 9);
        temporary.put("V", 5);
       // temporary.put("IV", 4);
        temporary.put("I", 1);
        literals = Collections.unmodifiableMap(temporary);
    }

    public static RomanNumberBuilder builder (){
        return new RomanNumberBuilder();

    }
    public static class RomanNumberBuilder {
        private String repr;

        public RomanNumberBuilder setRepr(String repr) {
            this.repr = repr;
            return this;
        }

        public RomanNumber createRomanNumber() {
            return new RomanNumber(repr);
        }
    }

}
