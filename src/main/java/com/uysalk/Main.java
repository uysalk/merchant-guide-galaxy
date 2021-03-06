package com.uysalk;

import com.uysalk.galactic.InterGalacticUnitRegistry;
import com.uysalk.metal.MetalRegistry;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by uysal.kara on 13.01.2017.
 */
public class Main {

        public static void main(String... args) throws Exception {

            URI fileURI =  MerchantProgram.class.getResource( "/input.txt" ).toURI();
            final InterGalacticUnitRegistry interGalacticUnitRegistry = new InterGalacticUnitRegistry();
            final MetalRegistry metalRegistry = new MetalRegistry();

            MerchantProgram merchantProgram = new MerchantProgram(interGalacticUnitRegistry, metalRegistry);
            try (Stream<String> stream = Files.lines(Paths.get(fileURI))) {

                stream.map((line)->merchantProgram.interpret(line)).
                        map((x)->x.getResponse().toString()).
                        filter (((Predicate<String>) String::isEmpty).negate()).
                        forEach(System.out::println);

            }

        }
}
